package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.utils.dtos.AuthorizationDTO;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class GraphQlClientFactory {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final RedisService redisService;

    public HttpGraphQlClient createMainUserClient() {
        WebClient webClient =
                mainUserWebClientConfigs()
                        .baseUrl(ubiServiceConfiguration.getGraphqlUrl())
                        .build();
        return HttpGraphQlClient.builder(webClient).build();
    }

    public HttpGraphQlClient createAuthorizedUserClient(AuthorizationDTO authorizationDTO) {
        WebClient webClient =
                authorizedUserWebClientConfigs(authorizationDTO)
                        .baseUrl(ubiServiceConfiguration.getGraphqlUrl())
                        .build();
        return HttpGraphQlClient.builder(webClient).build();
    }

    private WebClient.Builder mainUserWebClientConfigs() {
        return anyUserWebClientConfigs()
                .defaultHeader("Authorization", redisService.getMainUserAuthorizationToken())
                .defaultHeader("Ubi-SessionId", redisService.getMainUserSessionId())
                .defaultHeader("Ubi-ProfileId", redisService.getMainUserProfileId())
                .defaultHeader("Ubi-SpaceId", redisService.getGameSpaceId());
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
                .defaultHeader("Content-Type", ubiServiceConfiguration.getContentType())
                .defaultHeader("Ubi-AppId", ubiServiceConfiguration.getUbiAppId())
                .defaultHeader("Ubi-RegionId", ubiServiceConfiguration.getRegionId())
                .defaultHeader("Ubi-LocaleCode", ubiServiceConfiguration.getLocaleCode())
                .defaultHeader("User-Agent", ubiServiceConfiguration.getUserAgent());
    }
}
