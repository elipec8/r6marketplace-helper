package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.client.executors.InputState;

public class CredentialsAddInputPassword extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput(updateInfo, "Credentials successfully provided.");

        botService.addCredentialsFromUserInputs(updateInfo.getChatId());
    }
}
