package github.ricemonger.item_trade_stats_calculator.scheduled_tasks;

import github.ricemonger.item_trade_stats_calculator.services.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledAllItemsStatsRecalculator {

    private final ItemService itemService;

    @Scheduled(fixedRate = 20 * 60 * 1000, initialDelay = 9 * 60 * 1000) // every 20m after 9m of delay
    public void recalculateAndSaveAllItemsHistoryFields() {
        itemService.recalculateAndSaveAllItemsHistoryFields();
    }
}
