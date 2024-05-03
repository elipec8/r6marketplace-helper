package github.ricemonger.telegramBot.client.executors.credentials.remove;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveAllConfirmedCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserAllCredentials(updateInfo.getChatId());
    }
}
