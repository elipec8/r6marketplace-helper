package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class UbiAccountEntryAuthorizeStage2AskPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.UBI_ACCOUNT_ENTRY_PASSWORD, "Please provide your Ubisoft password:");
    }
}
