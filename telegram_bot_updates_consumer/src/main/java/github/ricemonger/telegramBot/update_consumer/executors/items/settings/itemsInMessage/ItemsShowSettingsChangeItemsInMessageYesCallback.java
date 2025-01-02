package github.ricemonger.telegramBot.update_consumer.executors.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeItemsInMessageYesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(updateInfo.getChatId(), true);
        sendText("Few Items will be shown in one message");
    }
}
