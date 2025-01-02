package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.newManagersAreActiveFlag;

import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_set_false_flag() {
        TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallback commandExecutor = new TradeManagersSettingsChangeNewManagersAreActiveFlagNoCallback();
        commandExecutor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).setUserTradeManagersSettingsNewManagersAreActiveFlag(MockUpdateInfos.UPDATE_INFO.getChatId(), false);

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}