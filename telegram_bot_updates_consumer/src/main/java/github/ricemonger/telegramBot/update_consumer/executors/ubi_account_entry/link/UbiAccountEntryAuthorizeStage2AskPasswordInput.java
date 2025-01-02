package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class UbiAccountEntryAuthorizeStage2AskPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.UBI_ACCOUNT_ENTRY_PASSWORD, "Please provide your Ubisoft password:");
    }
}
