package github.ricemonger.telegramBot.executors.marketplace.items.show.showByRequest;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class ItemsShowByRequestCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.FILTER_TYPE, InputGroup.ITEM_SHOW_BY_REQUEST);

        CallbackButton allow = new CallbackButton("Allow", Callbacks.FILTER_TYPE_ALLOW);
        CallbackButton deny = new CallbackButton("Deny", Callbacks.FILTER_TYPE_DENY);
        askFromInlineKeyboard("Please choose filter type:",2,allow,deny);
    }
}
