package github.ricemonger.telegramBot.executors.marketplace.items.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowStage2FinishInput extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.showItemsByUserSettingsAndInputtedOffset(updateInfo.getChatId());
    }
}
