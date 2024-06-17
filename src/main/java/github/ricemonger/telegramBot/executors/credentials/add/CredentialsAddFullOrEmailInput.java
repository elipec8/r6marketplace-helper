package github.ricemonger.telegramBot.executors.credentials.add;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputState;

public class CredentialsAddFullOrEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {

        String userInput = getUserCurrentInput();

        if(userInput.contains(":")){

            processLastInput("Credentials successfully provided.");

            botInnerService.addCredentialsFromUserInputs(updateInfo.getChatId());
        }
        else {
            processMiddleInput(InputState.CREDENTIALS_PASSWORD, "Please provide your Ubisoft password:");
        }
    }
}
