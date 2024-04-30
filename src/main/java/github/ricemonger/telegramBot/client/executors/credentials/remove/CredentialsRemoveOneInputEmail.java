package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveOneInputEmail extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput(updateInfo, "Email successfully provided.");

        botService.removeCredentialsByUserInputs(updateInfo.getChatId());
    }
}
