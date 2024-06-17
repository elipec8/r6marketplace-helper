package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputState;

public class FilterEditStage3AskIsOwnedInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_IS_OWNED);
        CallbackButton owned = new CallbackButton("Yes", Callbacks.FILTER_ITEM_IS_OWNED);
        CallbackButton notOwned = new CallbackButton("No", Callbacks.FILTER_ITEM_IS_NOT_OWNED);
        CallbackButton any = new CallbackButton("Skip", Callbacks.FILTER_ITEM_IS_OWNED_ANY);
        askFromInlineKeyboard("Please choose if item should be owned or not.",3, owned, notOwned,any);
    }
}
