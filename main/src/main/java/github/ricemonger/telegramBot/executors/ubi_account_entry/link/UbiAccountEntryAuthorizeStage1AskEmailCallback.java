package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class UbiAccountEntryAuthorizeStage1AskEmailCallback extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        String text = "Please provide your Ubisoft Account's email:";

        processFirstInput(InputState.UBI_ACCOUNT_ENTRY_EMAIL, InputGroup.UBI_ACCOUNT_ENTRY_LINK, text);
    }
}
