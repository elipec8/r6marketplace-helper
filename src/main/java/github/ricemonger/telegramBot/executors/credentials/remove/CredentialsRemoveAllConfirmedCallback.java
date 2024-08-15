package github.ricemonger.telegramBot.executors.credentials.remove;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsRemoveAllConfirmedCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserUbiAccountEntry(updateInfo.getChatId());
    }
}
