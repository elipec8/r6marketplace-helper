package github.ricemonger.marketplace.graphQl;


import github.ricemonger.utils.DTOs.personal.auth.AuthorizationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public class GraphQlClientFactory {

    private final GraphQlCommonValuesService graphQlCommonValuesService;

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
                .defaultHeader("Authorization", graphQlCommonValuesService.getMainUserAuthorizationToken())
                .defaultHeader("Ubi-SessionId", graphQlCommonValuesService.getMainUserSessionId())
                .defaultHeader("Ubi-ProfileId", graphQlCommonValuesService.getMainUserProfileId())
                .defaultHeader("Ubi-SpaceId", graphQlCommonValuesService.getUbiGameSpaceId());
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
                .defaultHeader("Content-Type", graphQlCommonValuesService.getContentType())
                .defaultHeader("Ubi-AppId", graphQlCommonValuesService.getUbiBaseAppId())
                .defaultHeader("Ubi-RegionId", graphQlCommonValuesService.getUbiRegionId())
                .defaultHeader("Ubi-LocaleCode", graphQlCommonValuesService.getUbiLocaleCode())
                .defaultHeader("User-Agent", graphQlCommonValuesService.getUserAgent())
                .baseUrl(graphQlCommonValuesService.getGraphqlUrl());
    }
}
