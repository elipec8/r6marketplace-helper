package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInputTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_to_update_expression_and_process_last_input_if_expression_valid() {
        TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput executor = new TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput();

        when(botInnerService.getUserInputByState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION)).thenReturn("expressionValid");
        when(botInnerService.isTradePriorityExpressionValid("expressionValid")).thenReturn(true);

        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserInputByState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION);
        verify(botInnerService).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);
        verify(botInnerService).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }

    @Test
    public void initAndExecute_should_ask_to_reenter_expression_invalid() {
        TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput executor = new TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput();

        when(botInnerService.getUserInputByState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION)).thenReturn("expressionValid");
        when(botInnerService.isTradePriorityExpressionValid("expressionValid")).thenReturn(false);


        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserInputByState(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION);

        verify(botInnerService, times(0)).saveUserInput(MockUpdateInfos.UPDATE_INFO);
        verify(botInnerService, times(0)).setUserInputStateAndGroup(MockUpdateInfos.UPDATE_INFO.getChatId(), InputState.BASE, InputGroup.BASE);
        verify(botInnerService, times(0)).askFromInlineKeyboard(eq(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }
}