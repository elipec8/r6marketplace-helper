package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

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
