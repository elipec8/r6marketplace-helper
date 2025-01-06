package github.ricemonger.item_trade_stats_calculator.scheduled_tasks;

import github.ricemonger.item_trade_stats_calculator.services.TradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledUsersCurrentTradesPotentialTradeStatsCalculator {

    private final TradeService tradeService;

    @Scheduled(fixedRateString = "${app.scheduling.trades.fixedRate}", initialDelayString = "${app.scheduling.trades.initialDelay}")
    public void recalculateAndSaveUsersCurrentTradesPotentialTradeStats() {
        tradeService.recalculateAndSaveUsersCurrentTradesPotentialTradeStats();
    }
}
