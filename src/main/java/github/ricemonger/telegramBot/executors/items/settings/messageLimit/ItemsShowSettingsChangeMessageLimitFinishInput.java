package github.ricemonger.telegramBot.executors.items.settings.messageLimit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeMessageLimitFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(updateInfo.getChatId());

        sendText("Message limit has been changed to " + botInnerService.getUserItemShowSettings(updateInfo.getChatId()).getItemShowMessagesLimit());
    }
}
