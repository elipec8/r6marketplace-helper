package github.ricemonger.marketplace.graphQl;

import github.ricemonger.marketplace.UbiServiceConfiguration;
import github.ricemonger.marketplace.databases.redis.services.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class GraphQlClientFactory {

    private final UbiServiceConfiguration ubiServiceConfiguration;

    private final RedisService redisService;

    private Date mainUserExpires;

    private HttpGraphQlClient mainUserGraphQlClient;

    public HttpGraphQlClient getOrCreateAllItemsStatsFetcherClient() {

        if(mainUserGraphQlClient == null || mainUserExpires.before(new Date())){
            mainUserGraphQlClient = buildMainUserGraphQlClient(ubiServiceConfiguration.getUpdateItemsUrl());
            mainUserExpires = new Date(System.currentTimeMillis() + ubiServiceConfiguration.getExpireTimeout() * 1000L); // 2.5h
        }

        return mainUserGraphQlClient;
    }

    private HttpGraphQlClient buildMainUserGraphQlClient(String url){
        WebClient webClient =
                mainUserWebClientConfigs()
                        .baseUrl(url)
                        .build();
        return HttpGraphQlClient.builder(webClient).build();
    }
    private WebClient.Builder mainUserWebClientConfigs(){

        int size = 1024 * 1024 * 10; // 10MB
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .defaultHeader("Authorization", redisService.getMainUserAuthorizationToken())
                .defaultHeader("Ubi-SessionId", redisService.getMainUserSessionId())
                .defaultHeader("Ubi-ProfileId", redisService.getMainUserProfileId())
                .defaultHeader("Ubi-SpaceId", redisService.getMainUserSpaceId())
                .defaultHeader("Content-Type", ubiServiceConfiguration.getContentType())
                .defaultHeader("Ubi-AppId", ubiServiceConfiguration.getUbiAppId())
                .defaultHeader("Ubi-RegionId", ubiServiceConfiguration.getRegionId())
                .defaultHeader("Ubi-LocaleCode", ubiServiceConfiguration.getLocaleCode())
                .defaultHeader("User-Agent", ubiServiceConfiguration.getUserAgent());
    }
}
