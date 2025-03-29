package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradePriorityExpression;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class TradeManagersSettingsChangeSellTradePriorityExpressionAskCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String text = "Please enter new sell trade priority expression: \n";

        processFirstInput(InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION, InputGroup.TRADE_MANAGERS_SETTINGS_CHANGE_SELL_TRADE_PRIORITY_EXPRESSION, text);
    }
}
