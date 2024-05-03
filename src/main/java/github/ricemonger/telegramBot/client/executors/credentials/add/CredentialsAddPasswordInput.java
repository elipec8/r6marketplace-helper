package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsAddPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput(updateInfo, "Credentials successfully provided.");

        botInnerService.addCredentialsFromUserInputs(updateInfo.getChatId());
    }
}
