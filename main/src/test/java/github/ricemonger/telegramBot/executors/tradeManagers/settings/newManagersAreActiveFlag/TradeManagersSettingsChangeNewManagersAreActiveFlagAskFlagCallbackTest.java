package github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.newManagersAreActiveFlag.TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_yes_or_no_from_inline_keyboard() {
        TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback commandExecutor = new TradeManagersSettingsChangeNewManagersAreActiveFlagAskFlagCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}