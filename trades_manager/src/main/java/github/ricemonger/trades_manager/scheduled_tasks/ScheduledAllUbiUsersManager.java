package github.ricemonger.trades_manager.scheduled_tasks;

import github.ricemonger.trades_manager.services.CentralTradeManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllUbiUsersManager {

    public static final int TRADE_MANAGER_FIXED_RATE_MINUTES = 1;

    private final CentralTradeManager centralTradeManager;

    @Scheduled(fixedRate = TRADE_MANAGER_FIXED_RATE_MINUTES * 60 * 1000, initialDelay = 5 * 60 * 1000) // every 1m after 5m of delay
    public void manageAllUsersTrades() {
        centralTradeManager.manageAllUsersTrades();
    }
}
