package github.ricemonger.telegramBot.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeItemsInMessageNoCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(updateInfo.getChatId(), false);
        sendText("One item will be shown per one message");
    }
}
