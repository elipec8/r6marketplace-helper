package github.ricemonger.marketplace.authorization;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.auth.AuthorizationDTO;
import github.ricemonger.utils.DTOs.auth.TwoFaBaseAuthDTO;
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

    public AuthorizationDTO authorizeAndGet2FaAuthorizedDTO(String twoFaCode, String twoFaToken) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", twoFaToken)
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiTwoFaAppId())
                .defaultHeader("Ubi-2FaCode", twoFaCode)
                .build();

        AuthorizationDTO dto = new AuthorizationDTO();

        try {

            dto = webClient
                    .post()
                    .bodyValue(new TwoFaAuthorizationBodyValue(true, new TwoFaAuthorizationBodyValueTrustedDevice(commonValuesService.getTrustedDeviceId(), commonValuesService.getTrustedDeviceFriendlyName())))
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
            log.error("Server error during ubi 2fa authorization for {}:{} :" + e, twoFaCode, twoFaToken);
        }

        return dto;
    }

    public TwoFaBaseAuthDTO authorizeAndGetBase2FaDtoForEncodedPassword(String email, String encodedPassword) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        return authorizeAndGetBase2FaDTO(email, AESPasswordEncoder.decode(encodedPassword));
    }

    public TwoFaBaseAuthDTO authorizeAndGetBase2FaDTO(String email, String password) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", getBasicTokenForCredentials(email, password))
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiTwoFaAppId())
                .build();

        TwoFaBaseAuthDTO dto = new TwoFaBaseAuthDTO();

        try {

            dto = webClient
                    .post()
                    .bodyValue(new BasicAuthorizationBodyValue(true))
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
                    .bodyToMono(TwoFaBaseAuthDTO.class)
                    .block();

            if (dto != null) {
                dto.setTwoFactorAuthenticationTicket("ubi_2fa_v1 t=" + dto.getTwoFactorAuthenticationTicket());
            }
        } catch (UbiUserAuthorizationServerErrorException e) {
            log.error("Server error during ubi base 2Fa authorization for {}:{} :" + e, email, password);
        }

        return dto;
    }

    public AuthorizationDTO authorizeAndGetBaseAuthorizedDtoForEncodedPassword(String email, String encodedPassword) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        return authorizeAndGetBaseAuthorizedDTO(email, AESPasswordEncoder.decode(encodedPassword));
    }

    public AuthorizationDTO authorizeAndGetBaseAuthorizedDTO(String email, String password) throws UbiUserAuthorizationClientErrorException, UbiUserAuthorizationServerErrorException {
        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", getBasicTokenForCredentials(email, password))
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiBaseAppId())
                .build();

        AuthorizationDTO dto = new AuthorizationDTO();

        try {

            dto = webClient
                    .post()
                    .bodyValue(new BasicAuthorizationBodyValue(true))
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
            log.error("Server error during ubi base authorization for {}:{} :" + e, email, password);
        }

        return dto;
    }

    public String getEncodedPassword(String password) {
        return AESPasswordEncoder.encode(password);
    }

    private String getBasicTokenForCredentials(String email, String password) {

        String token = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes());

        System.out.println("Basic " + token);

        return "Basic " + token;
    }

    private record TwoFaAuthorizationBodyValue(boolean rememberMe, TwoFaAuthorizationBodyValueTrustedDevice trustedDevice) {
    }

    private record TwoFaAuthorizationBodyValueTrustedDevice(String id, String friendlyName) {
    }

    private record BasicAuthorizationBodyValue(boolean rememberMe) {
    }
}
