package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression;

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

@SpringBootTest
class TradeManagersSettingsChangeBuyTradePriorityExpressionAskCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        TradeManagersSettingsChangeBuyTradePriorityExpressionAskCallback executor = new TradeManagersSettingsChangeBuyTradePriorityExpressionAskCallback();
        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION, InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION);
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}