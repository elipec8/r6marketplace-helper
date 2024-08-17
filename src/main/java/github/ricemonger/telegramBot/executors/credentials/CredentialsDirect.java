package github.ricemonger.telegramBot.executors.credentials;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsDirect extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        MyFunctionalInterface command = () -> askFromInlineKeyboard(
                "Please choose the action with your Ubisoft account:",
                1,
                new CallbackButton("Link Ubisoft Account", Callbacks.CREDENTIALS_ADD),
                new CallbackButton("Unlink Ubisoft Account", Callbacks.CREDENTIALS_REMOVE),
                new CallbackButton("Show Linked Account's Email", Callbacks.CREDENTIALS_SHOW)
        );
        executeCommandOrAskToRegister(command);
    }
}
