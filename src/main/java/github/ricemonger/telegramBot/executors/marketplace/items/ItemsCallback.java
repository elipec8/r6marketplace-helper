package github.ricemonger.telegramBot.executors.marketplace.items;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        CallbackButton byRequest = new CallbackButton("Show By Request", Callbacks.ITEMS_SHOW_BY_REQUEST);
        askFromInlineKeyboard("Please choose operation:",1,byRequest);
    }
}
