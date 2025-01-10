package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.edit;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.enums.ItemType;

import java.util.Arrays;

public class FilterEditStage4AskItemTypesInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEM_FILTER_ITEM_TYPES);
        String types = Arrays.stream(ItemType.values()).map(Enum::name).reduce((s, s2) -> s + "," + s2).orElse("");
        askFromInlineKeyboardOrSkip("Please enter item types, dived by \",\" or \"|\". Types list:\n" + types, 1);
    }
}
