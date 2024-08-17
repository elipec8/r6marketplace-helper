package github.ricemonger.telegramBot.executors.credentials.unlink;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsUnlinkConfirmedCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserUbiAccountEntry(updateInfo.getChatId());
    }
}
