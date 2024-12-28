package github.ricemonger.trades_manager.scheduled_tasks;

import github.ricemonger.trades_manager.services.CentralTradeManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ScheduledAllUbiUsersManagerTest {
    @Autowired
    private ScheduledAllUbiUsersManager scheduledAllUbiUsersManager;
    @MockBean
    private CentralTradeManager centralTradeManager;

    @Test
    public void manageAllUsersTrades_should_handle_to_service(){
        scheduledAllUbiUsersManager.manageAllUsersTrades();

        verify(centralTradeManager).manageAllUsersTrades();
    }

}