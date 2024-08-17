package github.ricemonger.telegramBot.executors.items.settings.shownFields;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeShownFieldsStage8FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.setUserItemShownFieldsSettingByUserInput(updateInfo.getChatId());

        sendText("Settings have been saved.");
    }
}
