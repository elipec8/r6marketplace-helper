package github.ricemonger.configs_fetcher.services;

import github.ricemonger.utils.DTOs.common.ConfigResolvedTransactionPeriod;
import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.abstract_services.CommonValuesDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private CommonValuesDatabaseService commonValuesDatabaseService;

    @Test
    public void setConfigResolvedTransactionPeriod_should_handle_to_service() {
        ConfigResolvedTransactionPeriod configResolvedTransactionPeriod = Mockito.mock(ConfigResolvedTransactionPeriod.class);
        commonValuesService.setConfigResolvedTransactionPeriod(configResolvedTransactionPeriod);

        verify(commonValuesDatabaseService).setConfigResolvedTransactionPeriod(same(configResolvedTransactionPeriod));
    }

    @Test
    public void setConfigTrades_should_handle_to_service() {
        ConfigTrades configTrades = Mockito.mock(ConfigTrades.class);
        commonValuesService.setConfigTrades(configTrades);

        verify(commonValuesDatabaseService).setConfigTrades(same(configTrades));
    }
}