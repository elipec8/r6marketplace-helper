package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.redis.services.RedisService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledMarketplaceConfigurationFetcher {

    private final RedisService redisService;

    private final GraphQlClientService graphQlClientService;

    private final TagService tagService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // every 24h with no delay
    public void fetchMarketplaceConfigurations() {







    }
}
