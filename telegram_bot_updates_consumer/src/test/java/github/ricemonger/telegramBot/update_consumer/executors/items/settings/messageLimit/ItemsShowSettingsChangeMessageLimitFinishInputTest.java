package github.ricemonger.telegramBot.update_consumer.executors.items.settings.messageLimit;

import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemsShowSettingsChangeMessageLimitFinishInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_last_input_and_set_new_setting_and_notify_user() {
        ItemsShowSettingsChangeMessageLimitFinishInput commandExecutor = new ItemsShowSettingsChangeMessageLimitFinishInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}