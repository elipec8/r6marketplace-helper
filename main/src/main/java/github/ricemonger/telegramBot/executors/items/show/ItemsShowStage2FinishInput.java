package github.ricemonger.telegramBot.executors.items.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowStage2FinishInput extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(updateInfo.getChatId());
    }
}
