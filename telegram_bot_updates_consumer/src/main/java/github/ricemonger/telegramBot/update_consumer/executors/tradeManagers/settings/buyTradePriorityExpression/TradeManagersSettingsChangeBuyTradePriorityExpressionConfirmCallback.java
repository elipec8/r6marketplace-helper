package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.buyTradePriorityExpression;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeBuyTradePriorityExpressionConfirmCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersBuySettingsTradePriorityExpressionByUserInput(updateInfo.getChatId());

        sendText("Trade priority expression was successfully saved.");
    }
}
