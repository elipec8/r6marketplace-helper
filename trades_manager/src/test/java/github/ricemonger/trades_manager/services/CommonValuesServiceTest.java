package github.ricemonger.trades_manager.services;

import github.ricemonger.utils.DTOs.common.ConfigTrades;
import github.ricemonger.utils.abstractions.CommonValuesDatabaseService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CommonValuesServiceTest {
    @Autowired
    private CommonValuesService commonValuesService;
    @MockBean
    private CommonValuesDatabaseService commonValuesDatabaseService;

    @Test
    public void getConfigTrades_should_return_service_result(){
        ConfigTrades configTrades = Mockito.mock(ConfigTrades.class);

        when(commonValuesDatabaseService.getConfigTrades()).thenReturn(configTrades);

        assertSame(configTrades, commonValuesService.getConfigTrades());
    }
}