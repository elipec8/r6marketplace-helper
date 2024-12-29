package github.ricemonger.telegramBot.update_consumer.executors.items.settings.messageLimit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.DTOs.personal.ItemShowSettings;

public class ItemsShowSettingsChangeMessageLimitFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        ItemShowSettings settings = botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(updateInfo.getChatId());

        sendText("Message limit has been changed to " + settings.getItemShowMessagesLimit());
    }
}
