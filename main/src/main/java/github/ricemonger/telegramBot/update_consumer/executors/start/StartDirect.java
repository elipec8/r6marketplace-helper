package github.ricemonger.telegramBot.update_consumer.executors.start;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

import static github.ricemonger.telegramBot.Callbacks.CANCEL;
import static github.ricemonger.telegramBot.Callbacks.START_YES;

public class StartDirect extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        if (isRegistered()) {
            sendText("You are already registered");
        } else {
            askYesOrNoFromInlineKeyboard("Do you want to register?", START_YES, CANCEL);
        }
    }
}
