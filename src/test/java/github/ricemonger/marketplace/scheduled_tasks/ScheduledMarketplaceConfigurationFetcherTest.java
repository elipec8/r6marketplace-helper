package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.CommonValuesService;
import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.TagService;
import github.ricemonger.utils.dtos.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.dtos.ConfigTrades;
import github.ricemonger.utils.dtos.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledMarketplaceConfigurationFetcherTest {

    @MockBean
    private CommonValuesService commonValuesService;

    @MockBean
    private GraphQlClientService graphQlClientService;

    @MockBean
    private TagService tagService;

    @Autowired
    private ScheduledMarketplaceConfigurationFetcher scheduledMarketplaceConfigurationFetcher;

    @Test
    void fetchMarketplaceConfigurations_should_call_services() {
        List<Tag> tags = new ArrayList<>();

        ConfigResolvedTransactionPeriod configResolvedTransactionPeriod = new ConfigResolvedTransactionPeriod(1, 2);

        ConfigTrades configTrades = new ConfigTrades();

        when(graphQlClientService.fetchAllTags()).thenReturn(tags);
        when(graphQlClientService.fetchConfigResolvedTransactionPeriod()).thenReturn(configResolvedTransactionPeriod);
        when(graphQlClientService.fetchConfigTrades()).thenReturn(configTrades);

        scheduledMarketplaceConfigurationFetcher.fetchMarketplaceConfigurations();

        verify(tagService).saveAll(same(tags));
        verify(graphQlClientService).checkItemTypes();

        verify(commonValuesService).setConfigResolvedTransactionPeriod(same(configResolvedTransactionPeriod));
        verify(commonValuesService).setConfigTrades(same(configTrades));
    }
}