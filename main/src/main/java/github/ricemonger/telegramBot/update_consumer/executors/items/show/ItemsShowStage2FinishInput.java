package github.ricemonger.telegramBot.update_consumer.executors.items.show;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowStage2FinishInput extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.sendItemsByUserItemShowSettingsAndUserInputOffset(updateInfo.getChatId());
    }
}
