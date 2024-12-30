package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.InputState;

import java.util.Collection;

public class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);

        ItemFilter filter = botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(updateInfo);

        Collection<String> appliedFilters = botInnerService.getItemShowAppliedFiltersNames(updateInfo.getChatId());

        String text;

        if (appliedFilters.contains(filter.getName())) {
            text = "Chosen filter is:\n" + filter.toHandsomeString() + "\nWould you like to remove it?";
        } else {
            text = "Chosen filter is:\n" + filter.toHandsomeString() + "\nWould you like to add it?";
        }

        askYesOrNoFromInlineKeyboard(text, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
