package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveOneEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput(updateInfo, "Email successfully provided.");

        botInnerService.removeCredentialsByUserInputs(updateInfo.getChatId());
    }
}
