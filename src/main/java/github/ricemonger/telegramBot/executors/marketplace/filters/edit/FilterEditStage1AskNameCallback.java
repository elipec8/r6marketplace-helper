package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;

public class FilterEditStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.FILTER_NAME, InputGroup.FILTERS_EDIT, """
                Please enter:
                New name to crete new filter
                OR
                Existing filter's name to update filter
                """);
    }
}
