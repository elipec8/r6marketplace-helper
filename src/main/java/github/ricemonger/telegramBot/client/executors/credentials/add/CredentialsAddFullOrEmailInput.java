package github.ricemonger.telegramBot.client.executors.credentials.add;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.client.executors.InputState;

public class CredentialsAddFullOrEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {

        String userInput = getUserCurrentInput();

        if(userInput.contains(":")){

            processLastInput(updateInfo, "Credentials successfully provided.");

            botService.addCredentialsFromUserInputs(updateInfo.getChatId());
        }
        else {
            processMiddleInput(updateInfo, InputState.CREDENTIALS_PASSWORD, "Please provide your Ubisoft password:");
        }
    }
}
