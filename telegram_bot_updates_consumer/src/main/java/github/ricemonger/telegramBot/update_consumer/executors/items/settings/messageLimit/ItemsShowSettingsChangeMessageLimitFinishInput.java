package github.ricemonger.telegramBot.update_consumer.executors.items.settings.messageLimit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeMessageLimitFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        Integer limit = botInnerService.setUserItemShowSettingsMessageLimitOrEdgeValueByUserInput(updateInfo.getChatId());

        sendText("Message limit has been changed to " + limit);
    }
}
