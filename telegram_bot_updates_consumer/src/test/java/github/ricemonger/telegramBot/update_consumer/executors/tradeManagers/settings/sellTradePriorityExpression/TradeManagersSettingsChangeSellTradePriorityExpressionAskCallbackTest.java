package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradePriorityExpression;

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
class TradeManagersSettingsChangeSellTradePriorityExpressionAskCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_process_first_input() {
        TradeManagersSettingsChangeSellTradePriorityExpressionAskCallback executor = new TradeManagersSettingsChangeSellTradePriorityExpressionAskCallback();
        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).clearUserInputsAndSetInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION, InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_TRADE_PRIORITY_EXPRESSION);
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}