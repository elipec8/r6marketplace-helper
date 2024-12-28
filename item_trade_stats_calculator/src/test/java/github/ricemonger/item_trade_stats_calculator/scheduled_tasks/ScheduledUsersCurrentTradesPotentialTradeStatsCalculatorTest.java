package github.ricemonger.item_trade_stats_calculator.scheduled_tasks;

import github.ricemonger.item_trade_stats_calculator.services.TradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduledUsersCurrentTradesPotentialTradeStatsCalculatorTest {
    @Autowired
    private ScheduledUsersCurrentTradesPotentialTradeStatsCalculator scheduledUsersCurrentTradesPotentialTradeStatsCalculator;
    @MockBean
    private TradeService tradeService;

    @Test
    public void recalculateAndSaveUsersCurrentTradesPotentialTradeStats_should_handle_to_service() {
        scheduledUsersCurrentTradesPotentialTradeStatsCalculator.recalculateAndSaveUsersCurrentTradesPotentialTradeStats();

        verify(tradeService).recalculateAndSaveUsersCurrentTradesPotentialTradeStats();
    }

}