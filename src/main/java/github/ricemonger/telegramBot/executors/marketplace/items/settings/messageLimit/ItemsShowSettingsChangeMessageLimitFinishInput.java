package github.ricemonger.telegramBot.executors.marketplace.items.settings.messageLimit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeMessageLimitFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.setItemShowSettingsMessageLimitByUserInput(updateInfo.getChatId());

        sendText("Message limit has been changed to " + botInnerService.getItemShowSettingsForUser(updateInfo.getChatId()).getItemShowMessagesLimit());
    }
}
