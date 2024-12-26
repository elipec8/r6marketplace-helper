package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduledAllItemsStatsRecalculatorTest {
    @Autowired
    private ScheduledAllItemsStatsRecalculator scheduledAllItemsStatsRecalculator;
    @MockBean
    private ItemService itemService;

    @Test
    public void recalculateAllItemsStats_should_call_services() {
        scheduledAllItemsStatsRecalculator.recalculateAndSaveAllItemsHistoryFields();
        verify(itemService).recalculateAndSaveAllItemsHistoryFields();
    }
}