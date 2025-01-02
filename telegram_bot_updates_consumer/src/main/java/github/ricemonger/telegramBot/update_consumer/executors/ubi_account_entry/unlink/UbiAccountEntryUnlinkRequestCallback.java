package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.unlink;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryUnlinkRequestCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askYesOrNoFromInlineKeyboard(
                "Are you sure you want to remove all your credentials?",
                Callbacks.UBI_ACCOUNT_ENTRY_UNLINK_FINISH_CONFIRMED,
                Callbacks.CANCEL);
    }
}
