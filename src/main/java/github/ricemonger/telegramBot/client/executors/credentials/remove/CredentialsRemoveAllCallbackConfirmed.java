package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveAllCallbackConfirmed extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botService.removeUserAllCredentials(updateInfo.getChatId());
    }
}
