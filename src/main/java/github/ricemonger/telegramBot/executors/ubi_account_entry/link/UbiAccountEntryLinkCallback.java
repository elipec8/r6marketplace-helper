package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryLinkCallback extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        String text = "Please provide your Ubisoft Account's email and password separated in two messages OR in the following format: email:password";

        processFirstInput(InputState.UBI_ACCOUNT_ENTRY_FULL_OR_EMAIL, InputGroup.UBI_ACCOUNT_ENTRY_LINK, text);
    }
}
