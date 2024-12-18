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

    private final static String AUTH_TICKET_PREFIX = "ubi_v1 t=";

    private final static String AUTH_TICKET_PREFIX_2FA = "ubi_2fa_v1 t=";

    private final CommonValuesService commonValuesService;

    private final AesPasswordEncoder AESPasswordEncoder;

    public AuthorizationDTO authorizeAndGet2FaAuthorizedDTOForEncodedPassword(String email, String encodedPassword, String twoFaCode) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        TwoFaBaseAuthDTO twoFaBaseAuthDTO = authorizeAndGetBase2FaDtoForEncodedPassword(email, encodedPassword);

        return authorizeAndGet2FaAuthorizedDTO(twoFaCode, twoFaBaseAuthDTO.getTwoFactorAuthenticationTicket());
    }

    public AuthorizationDTO authorizeAndGet2FaAuthorizedDTO(String email, String password, String twoFaCode) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        TwoFaBaseAuthDTO twoFaBaseAuthDTO = authorizeAndGetBase2FaDTO(email, password);

        return authorizeAndGet2FaAuthorizedDTO(twoFaCode, twoFaBaseAuthDTO.getTwoFactorAuthenticationTicket());
    }

    public AuthorizationDTO reauthorizeAndGet2FaAuthorizedDTO(String ticket) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {

        if (!ticket.startsWith(AUTH_TICKET_PREFIX)) {
            ticket = AUTH_TICKET_PREFIX + ticket;
        }

        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getAuthorizationUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", ticket)
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiTwoFaAppId())
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
                dto.setTicket(AUTH_TICKET_PREFIX + dto.getTicket());
            }
        } catch (UbiUserAuthorizationServerErrorException e) {
            log.error("Server error during ubi 2fa reauthorization for {}:" + e, ticket);
        }

        return dto;
    }

    public AuthorizationDTO authorizeAndGet2FaAuthorizedDTO(String twoFaCode, String twoFaToken) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {

        if (!twoFaToken.startsWith(AUTH_TICKET_PREFIX_2FA)) {
            twoFaToken = AUTH_TICKET_PREFIX_2FA + twoFaToken;
        }

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
                dto.setTicket(AUTH_TICKET_PREFIX + dto.getTicket());
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
                .defaultHeader("Authorization", createBasicTokenForCredentials(email, password))
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
                dto.setTwoFactorAuthenticationTicket(AUTH_TICKET_PREFIX_2FA + dto.getTwoFactorAuthenticationTicket());
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
                .defaultHeader("Authorization", createBasicTokenForCredentials(email, password))
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
                dto.setTicket(AUTH_TICKET_PREFIX + dto.getTicket());
            }
        } catch (UbiUserAuthorizationServerErrorException e) {
            log.error("Server error during ubi base authorization for {}:{} :" + e, email, password);
        }

        return dto;
    }

    public String encodePassword(String password) {
        return AESPasswordEncoder.encode(password);
    }

    private String createBasicTokenForCredentials(String email, String password) {

        String token = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes());

        return "Basic " + token;
    }

    //requires recaptcha
    private void request2FaCodeToSMS(String twoFaToken) throws UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {

        if (!twoFaToken.startsWith(AUTH_TICKET_PREFIX_2FA)) {
            twoFaToken = AUTH_TICKET_PREFIX_2FA + twoFaToken;
        }

        WebClient webClient = WebClient.builder()
                .baseUrl(commonValuesService.getTwoFaCodeToSmsUrl())
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .defaultHeader("Authorization", twoFaToken)
                .defaultHeader("Ubi-Appid", commonValuesService.getUbiTwoFaAppId())
                .build();


        try {

            webClient
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
                    .toBodilessEntity()
                    .block();

        } catch (UbiUserAuthorizationServerErrorException e) {
            log.error("Server error during ubi 2fa code to sms request for {}:" + e, twoFaToken);
        }
    }

    private record TwoFaAuthorizationBodyValue(boolean rememberMe, TwoFaAuthorizationBodyValueTrustedDevice trustedDevice) {
    }

    private record TwoFaAuthorizationBodyValueTrustedDevice(String id, String friendlyName) {
    }

    private record BasicAuthorizationBodyValue(boolean rememberMe) {
    }
}
