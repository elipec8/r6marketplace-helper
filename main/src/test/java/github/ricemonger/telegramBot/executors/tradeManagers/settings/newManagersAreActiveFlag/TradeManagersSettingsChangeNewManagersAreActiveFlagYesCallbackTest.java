package github.ricemonger.telegramBot.executors.tradeManagers.settings.newManagersAreActiveFlag;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeManagersSettingsChangeNewManagersAreActiveFlagYesCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_set_true_flag() {
        TradeManagersSettingsChangeNewManagersAreActiveFlagYesCallback commandExecutor = new TradeManagersSettingsChangeNewManagersAreActiveFlagYesCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserTradeManagersSettingsNewManagersAreActiveFlag(MockUpdateInfos.UPDATE_INFO.getChatId(), true);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}