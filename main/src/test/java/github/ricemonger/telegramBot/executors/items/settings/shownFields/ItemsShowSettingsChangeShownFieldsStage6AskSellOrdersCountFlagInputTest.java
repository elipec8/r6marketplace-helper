package github.ricemonger.telegramBot.executors.items.settings.shownFields;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.items.settings.shownFields.ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInput;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_keyboard() {
        ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInput commandExecutor = new ItemsShowSettingsChangeShownFieldsStage6AskSellOrdersCountFlagInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInputAndSetInputState(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_SELL_ORDERS_COUNT);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}