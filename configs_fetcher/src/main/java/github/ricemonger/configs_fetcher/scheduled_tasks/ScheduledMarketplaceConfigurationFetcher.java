package github.ricemonger.configs_fetcher.scheduled_tasks;

import github.ricemonger.configs_fetcher.services.CommonValuesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledMarketplaceConfigurationFetcher {

    private final CommonValuesService commonValuesService;

    private final ConfigQueryMarketplaceGraphQlClientService fetchAllTagsGraphQlClientService;

    private final ConfigQueryResolvedTransactionPeriodGraphQlClientService fetchConfigResolvedTransactionPeriodGraphQlClientService;

    private final TagService tagService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 20 * 1000) // every 24h with 20s delay
    public void fetchMarketplaceConfigurations() {
        tagService.saveAllTags(graphQlClientService.fetchAllTags());
        graphQlClientService.checkItemTypes();

        commonValuesService.setConfigResolvedTransactionPeriod(graphQlClientService.fetchConfigResolvedTransactionPeriod());
        commonValuesService.setConfigTrades(graphQlClientService.fetchConfigTrades());
    }
}
