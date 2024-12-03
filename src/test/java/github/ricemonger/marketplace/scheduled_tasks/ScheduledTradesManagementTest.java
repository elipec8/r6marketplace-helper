package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.CentralTradeManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduledTradesManagementTest {
    @Autowired
    private ScheduledTradesManagement scheduledTradesManagement;
    @MockBean
    private CentralTradeManager centralTradeManager;

    @Test
    public void manageAllUsersTrades_should_handle_to_service() {
        scheduledTradesManagement.manageAllUsersTrades();

        verify(centralTradeManager).manageAllUsersTrades();
    }
}