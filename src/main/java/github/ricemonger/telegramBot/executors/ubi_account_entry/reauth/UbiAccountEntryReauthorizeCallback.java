package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> askFromInlineKeyboard(
                "Please choose the action:",
                1,
                new CallbackButton("Change saved email, password and 2FA Code", Callbacks.UBI_ACCOUNT_ENTRY_REAUTHORIZE_FULL),
                new CallbackButton("Enter 2FA Code", Callbacks.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE)
        );
        executeCommandOrAskToRegister(command);
    }
}
