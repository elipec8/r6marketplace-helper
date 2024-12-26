package github.ricemonger.configs_fetcher.scheduled_tasks;

import github.ricemonger.configs_fetcher.services.CommonValuesService;
import github.ricemonger.configs_fetcher.services.TagService;
import github.ricemonger.marketplace.graphQl.config_query_marketplace.ConfigQueryMarketplaceGraphQlClientService;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.ConfigQueryResolvedTransactionPeriodGraphQlClient;
import github.ricemonger.marketplace.graphQl.config_query_trade.ConfigQueryTradeGraphQlClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledMarketplaceConfigurationFetcher {

    private final CommonValuesService commonValuesService;

    private final ConfigQueryMarketplaceGraphQlClientService configQueryMarketplaceGraphQlClientService;

    private final ConfigQueryResolvedTransactionPeriodGraphQlClient configQueryResolvedTransactionPeriodGraphQlClient;

    private final ConfigQueryTradeGraphQlClientService configQueryTradeGraphQlClientService;

    private final TagService tagService;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 20 * 1000) // every 24h with 20s delay
    public void fetchMarketplaceConfigurations() {
        tagService.saveAllTags(configQueryMarketplaceGraphQlClientService.fetchAllTags());
        configQueryMarketplaceGraphQlClientService.checkItemTypes();

        commonValuesService.setConfigResolvedTransactionPeriod(configQueryResolvedTransactionPeriodGraphQlClient.fetchConfigResolvedTransactionPeriod());
        commonValuesService.setConfigTrades(configQueryTradeGraphQlClientService.fetchConfigTrades());
    }
}
