package github.ricemonger.telegramBot.update_consumer.executors.items.show;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowStage2FinishInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_send_items_by_user_settings() {
        ItemsShowStage2FinishInput commandExecutor = new ItemsShowStage2FinishInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).sendItemsByUserItemShowSettingsAndUserInputOffset(MockUpdateInfos.UPDATE_INFO);
    }
}