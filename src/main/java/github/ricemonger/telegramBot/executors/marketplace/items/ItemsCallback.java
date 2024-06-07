package github.ricemonger.telegramBot.executors.marketplace.items;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        askFromInlineKeyboard("Please choose operation:",1);
    }
}
