package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.central_trade_manager.CentralTradeManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CentralTradeManagerTest {
    @Autowired
    private CentralTradeManager centralTradeManager;
    @MockBean
    private UserService userService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private CommonValuesService commonValuesService;

    @Test
    public void manageAllUsersTrades_should_create_and_execute_commands_for_all_manageable_users() {

    }
}