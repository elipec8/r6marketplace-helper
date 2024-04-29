package github.ricemonger.marketplace.authorization;

import github.ricemonger.marketplace.graphs.UbiServiceConfiguration;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    public AuthorizationDTO getUserAuthorizationDTO(String email, String password) {
        WebClient webClient = WebClient.builder()
                .baseUrl(ubiServiceConfiguration.getAuthorizationUrl())
                .defaultHeader("Content-Type", ubiServiceConfiguration.getContentType())
                .defaultHeader("User-Agent", ubiServiceConfiguration.getUserAgent())
                .defaultHeader("Authorization", getBasicTokenForCredentials(email,password))
                .defaultHeader("Ubi-Appid", ubiServiceConfiguration.getUbiAppId())
                .build();

        AuthorizationDTO dto = webClient.post().bodyValue(new AuthorizationBodyValue(true)).retrieve().bodyToMono(AuthorizationDTO.class).block();
        dto.setTicket("ubi_v1 t=" + dto.getTicket());
        return dto;
    }

    private String getBasicTokenForCredentials(String email, String password) {

        String token = Base64.getEncoder().encodeToString(String.format("%s:%s", email, password).getBytes()) ;

        return "Basic " + token;
    }
}
