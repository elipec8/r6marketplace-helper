package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeManagersSettingsChangeBuyTradePriorityExpressionAskCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String text = "Please enter new buy trade priority expression: \n";

        processFirstInput(InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION, InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION, text);
    }
}
