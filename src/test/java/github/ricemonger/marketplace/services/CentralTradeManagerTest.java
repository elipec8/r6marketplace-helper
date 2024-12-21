package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.graphQl.GraphQlClientService;
import github.ricemonger.marketplace.services.factories.CentralTradeManagerCommandFactory;
import github.ricemonger.marketplace.services.factories.PersonalItemFactory;
import github.ricemonger.marketplace.services.factories.PotentialTradeFactory;
import github.ricemonger.telegramBot.TelegramBotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CentralTradeManagerTest {
    @Autowired
    private CentralTradeManager centralTradeManager;
    @MockBean
    private GraphQlClientService graphQlClientService;
    @MockBean
    private TelegramBotService telegramBotService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ItemService itemService;
    @MockBean
    private UserService userService;
    @MockBean
    private PersonalItemFactory personalItemFactory;
    @MockBean
    private PotentialTradeFactory potentialTradeFactory;
    @MockBean
    private CentralTradeManagerCommandFactory centralTradeManagerCommandFactory;

    @Test
    public void manageAllUsersTrades_should_create_and_execute_commands_for_all_manageable_users() {

    }
}