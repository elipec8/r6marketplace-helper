package github.ricemonger.telegramBot.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.DTOs.items.ItemFilter;

import java.util.Collection;

public class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);

        ItemFilter filter = botInnerService.getUserItemFilterByUserInputCallbackFilterName(updateInfo.getChatId());

        Collection<ItemFilter> appliedFilters = botInnerService.getUserItemShowSettings(updateInfo.getChatId()).getItemShowAppliedFilters();

        String text;
        if (appliedFilters.contains(filter)) {
            text = "Chosen filter is:\n" + filter + "\nWould you like to remove it?";
        } else {
            text = "Chosen filter is:\n" + filter + "\nWould you like to add it?";
        }

        askYesOrNoFromInlineKeyboard(text, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
