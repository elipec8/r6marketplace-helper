package github.ricemonger.fetching_acounts_manager.authorization;

import github.ricemonger.fetching_acounts_manager.services.CommonValuesService;
import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final static String AUTH_TICKET_PREFIX = "ubi_v1 t=";

    private final CommonValuesService commonValuesService;

    public AuthorizationDTO authorizeAndGetBaseAuthorizedDTO(String email, String password) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", createBasicTokenForCredentials(email, password))
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiBaseAppId())
                .build();

        AuthorizationDTO dto = webClient
                .post()
                .bodyValue(new BasicAuthorizationBodyValue(true))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(new Function<String, Throwable>() {
                            @Override
                            public Throwable apply(String s) {
                                log.info("Client error during ubi base authorization for email {}:{} :", email, s);
                                return new UbiUserAuthorizationClientErrorException(s);
                            }
                        }))
                .onStatus(HttpStatusCode::is5xxServerError,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(new Function<String, Throwable>() {
                            @Override
                            public Throwable apply(String s) {
                                log.error("Server error during ubi base authorization for email {}:{} :", email, s);
                                return new UbiUserAuthorizationServerErrorException(s);
                            }
                        }))
                .bodyToMono(AuthorizationDTO.class)
                .block();

        if (dto != null) {
            dto.setTicket(AUTH_TICKET_PREFIX + dto.getTicket());
        }
        return dto;
    }

    private String createBasicTokenForCredentials(String email, String password) {

        String token = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes());

        return "Basic " + token;
    }

    private record BasicAuthorizationBodyValue(boolean rememberMe) {
    }
}
