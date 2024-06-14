package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveOneEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput("Email successfully provided.");

        botInnerService.removeCredentialsByUserInputs(updateInfo.getChatId());
    }
}
