package github.ricemonger.telegramBot.executors.marketplace.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeItemsInMessageNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setItemShowSettingsUserFewItemsInMessage(updateInfo.getChatId(), false);
        sendText("One item will be shown per one message");
    }
}
