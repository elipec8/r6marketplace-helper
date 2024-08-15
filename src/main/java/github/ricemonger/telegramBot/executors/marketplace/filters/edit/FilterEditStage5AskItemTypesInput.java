package github.ricemonger.telegramBot.executors.marketplace.filters.edit;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.ItemType;

import java.util.Arrays;

public class FilterEditStage5AskItemTypesInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.FILTER_ITEM_TYPES);
        String types = Arrays.stream(ItemType.values()).map(Enum::name).reduce((s, s2) -> s + "," + s2).orElse("");
        askFromInlineKeyboardOrSkip("Please enter item types, dived by \",\" or \"|\". Types list:\n" + types, 1);
    }
}
