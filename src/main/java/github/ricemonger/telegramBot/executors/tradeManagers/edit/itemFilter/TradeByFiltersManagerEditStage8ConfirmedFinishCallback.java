package github.ricemonger.telegramBot.executors.tradeManagers.edit.itemFilter;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class TradeByFiltersManagerEditStage8ConfirmedFinishCallback extends AbstractBotCommandExecutor{
    @Override
    protected void executeCommand() {
        botInnerService.saveUserTradeByFiltersManagerByUserInput(updateInfo.getChatId());

        sendText("Trade manager was successfully saved.");
    }
}
