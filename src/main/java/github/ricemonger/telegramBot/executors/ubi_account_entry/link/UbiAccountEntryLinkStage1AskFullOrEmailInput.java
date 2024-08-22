package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryLinkStage1AskFullOrEmailInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {

        String userInput = getUserCurrentInput();

        if (userInput.contains(":")) {

            processLastInput();

            botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());

            sendText("Credentials successfully provided.");
        } else {
            processMiddleInput(InputState.UBI_ACCOUNT_ENTRY_PASSWORD, "Please provide your Ubisoft password:");
        }
    }
}
