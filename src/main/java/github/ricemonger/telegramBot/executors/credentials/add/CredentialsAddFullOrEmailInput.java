package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsAddFullOrEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {

        String userInput = getUserCurrentInput();

        if (userInput.contains(":")) {

            processLastInput("Credentials successfully provided.");

            botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());
        } else {
            processMiddleInput(InputState.CREDENTIALS_PASSWORD, "Please provide your Ubisoft password:");
        }
    }
}
