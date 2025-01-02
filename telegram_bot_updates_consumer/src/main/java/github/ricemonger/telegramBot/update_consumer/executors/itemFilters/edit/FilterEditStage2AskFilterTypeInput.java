package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;

public class FilterEditStage2AskFilterTypeInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_TYPE);
        CallbackButton allow = new CallbackButton("Allow", Callbacks.ITEM_FILTER_TYPE_ALLOW);
        CallbackButton deny = new CallbackButton("Deny", Callbacks.ITEM_FILTER_TYPE_DENY);
        askFromInlineKeyboard("Please choose filter type:", 2, allow, deny);
    }
}
