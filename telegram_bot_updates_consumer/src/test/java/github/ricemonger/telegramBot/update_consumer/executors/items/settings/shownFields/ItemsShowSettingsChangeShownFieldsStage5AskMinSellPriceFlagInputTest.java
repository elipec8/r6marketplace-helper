package github.ricemonger.telegramBot.update_consumer.executors.items.settings.shownFields;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemsShowSettingsChangeShownFieldsStage5AskMinSellPriceFlagInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_middle_input_with_keyboard() {
        ItemsShowSettingsChangeShownFieldsStage5AskMinSellPriceFlagInput commandExecutor = new ItemsShowSettingsChangeShownFieldsStage5AskMinSellPriceFlagInput();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.ITEMS_SHOW_SETTING_SHOWN_FIELDS_MIN_SELL_PRICE);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}