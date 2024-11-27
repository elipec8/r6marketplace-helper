package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.utils.DTOs.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GraphQlClientFactory {

    private final CommonValuesService commonValuesService;

    public HttpGraphQlClient createMainUserClient() {
        WebClient webClient =
                mainUserWebClientConfigs()
                        .build();
        return HttpGraphQlClient.builder(webClient).build();
    }

    public HttpGraphQlClient createAuthorizedUserClient(AuthorizationDTO authorizationDTO) {
        WebClient webClient =
                authorizedUserWebClientConfigs(authorizationDTO)
                        .build();
        return HttpGraphQlClient.builder(webClient).build();
    }

    private WebClient.Builder mainUserWebClientConfigs() {
        return anyUserWebClientConfigs()
                .defaultHeader("Authorization", commonValuesService.getMainUserAuthorizationToken())
                .defaultHeader("Ubi-SessionId", commonValuesService.getMainUserSessionId())
                .defaultHeader("Ubi-ProfileId", commonValuesService.getMainUserProfileId())
                .defaultHeader("Ubi-SpaceId", commonValuesService.getUbiGameSpaceId());
    }

    private WebClient.Builder authorizedUserWebClientConfigs(AuthorizationDTO authorizationDTO) {

        return anyUserWebClientConfigs()
                .defaultHeader("Authorization", authorizationDTO.getTicket())
                .defaultHeader("Ubi-SessionId", authorizationDTO.getSessionId())
                .defaultHeader("Ubi-ProfileId", authorizationDTO.getProfileId())
                .defaultHeader("Ubi-SpaceId", authorizationDTO.getSpaceId());
    }

    private WebClient.Builder anyUserWebClientConfigs() {
        int size = 1024 * 1024 * 10; // 10MB
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeader("Content-Type", commonValuesService.getContentType())
                .defaultHeader("Ubi-AppId", commonValuesService.getUbiAppId())
                .defaultHeader("Ubi-RegionId", commonValuesService.getUbiRegionId())
                .defaultHeader("Ubi-LocaleCode", commonValuesService.getUbiLocaleCode())
                .defaultHeader("User-Agent", commonValuesService.getUserAgent())
                .baseUrl(commonValuesService.getGraphqlUrl());
    }
}
