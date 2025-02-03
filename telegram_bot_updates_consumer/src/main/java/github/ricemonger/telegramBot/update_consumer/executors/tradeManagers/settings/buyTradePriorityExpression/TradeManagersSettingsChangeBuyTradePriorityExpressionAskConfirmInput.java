package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class TradeManagersSettingsChangeBuyTradePriorityExpressionAskConfirmInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String expression = botInnerService.getUserInputByState(updateInfo.getChatId(), InputState.TRADE_MANAGERS_SETTINGS_TRADE_PRIORITY_EXPRESSION);

        boolean isExpressionValid = botInnerService.isTradePriorityExpressionValid(expression);

        if (isExpressionValid) {
            processLastInput();

            askYesOrNoFromInlineKeyboard(
                    "Expression " + expression + " is valid. Would you like to save it?",
                    Callbacks.TRADE_MANAGERS_SETTINGS_CHANGE_BUY_TRADE_PRIORITY_EXPRESSION_CONFIRMED,
                    Callbacks.CANCEL);
        } else {
            sendText("Expression is invalid, please try again.");
        }
    }
}
