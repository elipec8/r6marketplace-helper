package github.ricemonger.marketplace.authorization;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
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

    private final CommonValuesService commonValuesService;

    private final AesPasswordEncoder AESPasswordEncoder;

    public AuthorizationDTO authorizeAndGetDtoForEncodedPassword(String email, String encodedPassword) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        return authorizeAndGetDTO(email, AESPasswordEncoder.decode(encodedPassword));
    }

    public AuthorizationDTO authorizeAndGetDTO(String email, String password) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", getBasicTokenForCredentials(email, password))
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiAppId())
                .build();

        AuthorizationDTO dto = new AuthorizationDTO();

        try {

            dto = webClient
                    .post()
                    .bodyValue(new AuthorizationBodyValue(true))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            clientResponse -> clientResponse.bodyToMono(String.class).map(new Function<String, Throwable>() {
                                @Override
                                public Throwable apply(String s) {
                                    return new UbiUserAuthorizationClientErrorException(s);
                                }
                            }))
                    .onStatus(HttpStatusCode::is5xxServerError,
                            clientResponse -> clientResponse.bodyToMono(String.class).map(new Function<String, Throwable>() {
                                @Override
                                public Throwable apply(String s) {
                                    return new UbiUserAuthorizationServerErrorException(s);
                                }
                            }))
                    .bodyToMono(AuthorizationDTO.class)
                    .block();

            if (dto != null) {
                dto.setTicket("ubi_v1 t=" + dto.getTicket());
            }
        } catch (UbiUserAuthorizationServerErrorException e) {
            log.error("Server error during ubi authorization for {}:{} :" + e, email, password);
        }

        return dto;
    }

    public String getEncodedPassword(String password) {
        return AESPasswordEncoder.encode(password);
    }

    private String getBasicTokenForCredentials(String email, String password) {

        String token = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes());

        return "Basic " + token;
    }

    private record AuthorizationBodyValue(boolean rememberMe) {
    }
}
