package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> askFromInlineKeyboard(
                "Please choose the action with your Ubisoft account:",
                1,
                new CallbackButton("Link Ubisoft Account", Callbacks.UBI_ACCOUNT_ENTRY_LINK),
                new CallbackButton("Unlink Ubisoft Account", Callbacks.UBI_ACCOUNT_ENTRY_UNLINK_REQUEST),
                new CallbackButton("Show Linked Account's Email", Callbacks.UBI_ACCOUNT_ENTRY_SHOW),
                new CallbackButton("Reauthorize 2FA in Linked Account", Callbacks.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE)
        );
        executeCommandOrAskToRegister(command);
    }
}
