package github.ricemonger.telegramBot.update_consumer.executors.items.settings.shownFields;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input_with_keyboard() {
        ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback commandExecutor = new ItemsShowSettingsChangeShownFieldsStage1AskNameFlagCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_ITEM_NAME, InputGroup.ITEMS_SHOW_SETTING_CHANGE_SHOWN_FIELDS);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}