package github.ricemonger.telegramBot.executors.marketplace.items.settings.itemsInMessage;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeItemsInMessageYesCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.setUserItemShowSettingsFewItemsInMessageFlag(updateInfo.getChatId(), true);
        sendText("Few Items will be shown in one message");
    }
}
