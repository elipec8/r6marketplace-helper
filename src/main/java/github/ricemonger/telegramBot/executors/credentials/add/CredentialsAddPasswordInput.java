package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsAddPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput("Credentials successfully provided.");

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());
    }
}
