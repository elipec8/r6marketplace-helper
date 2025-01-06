package github.ricemonger.configs_fetcher.scheduled_tasks;

import github.ricemonger.configs_fetcher.services.CommonValuesService;
import github.ricemonger.configs_fetcher.services.TagService;
import github.ricemonger.marketplace.graphQl.config_query_marketplace.ConfigQueryMarketplaceGraphQlClientService;
import github.ricemonger.marketplace.graphQl.config_query_resolved_transactions_period.ConfigQueryResolvedTransactionPeriodGraphQlClient;
import github.ricemonger.marketplace.graphQl.config_query_trade.ConfigQueryTradeGraphQlClientService;
import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.DTOs.common.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledMarketplaceConfigurationFetcherTest {
    @Autowired
    private ScheduledMarketplaceConfigurationFetcher scheduledMarketplaceConfigurationFetcher;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ConfigQueryMarketplaceGraphQlClientService configQueryMarketplaceGraphQlClientService;
    @MockBean
    private ConfigQueryResolvedTransactionPeriodGraphQlClient configQueryResolvedTransactionPeriodGraphQlClient;
    @MockBean
    private ConfigQueryTradeGraphQlClientService configQueryTradeGraphQlClientService;
    @MockBean
    private TagService tagService;

    @Test
    void fetchMarketplaceConfigurations_should_call_services() {
        List<Tag> tags = new ArrayList<>();

        ConfigResolvedTransactionPeriod configResolvedTransactionPeriod = new ConfigResolvedTransactionPeriod(1, 2);

        ConfigTrades configTrades = new ConfigTrades();

        when(configQueryMarketplaceGraphQlClientService.fetchAllTags()).thenReturn(tags);
        when(configQueryResolvedTransactionPeriodGraphQlClient.fetchConfigResolvedTransactionPeriod()).thenReturn(configResolvedTransactionPeriod);
        when(configQueryTradeGraphQlClientService.fetchConfigTrades()).thenReturn(configTrades);

        scheduledMarketplaceConfigurationFetcher.fetchMarketplaceConfigurations();

        verify(tagService).saveAllTags(same(tags));
        verify(configQueryMarketplaceGraphQlClientService).checkItemTypes();

        verify(commonValuesService).setConfigResolvedTransactionPeriod(same(configResolvedTransactionPeriod));
        verify(commonValuesService).setConfigTrades(same(configTrades));
    }
}