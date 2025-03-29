package github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.settings.sellTradePriorityExpression;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class TradeManagersSettingsChangeSellTradePriorityExpressionConfirmCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserTradeManagersSellSettingsTradePriorityExpressionByUserInput(updateInfo.getChatId());

        sendText("Trade priority expression was successfully saved.");
    }
}
