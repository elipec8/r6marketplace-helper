package github.ricemonger.telegramBot.executors.itemFilters.edit;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class FilterEditStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.ITEM_FILTER_NAME, InputGroup.ITEM_FILTER_EDIT, """
                Please enter:
                New name to crete new filter
                OR
                Existing filter's name to update filter
                """);
    }
}
