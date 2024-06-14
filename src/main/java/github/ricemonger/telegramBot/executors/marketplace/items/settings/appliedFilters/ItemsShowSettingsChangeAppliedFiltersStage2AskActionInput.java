package github.ricemonger.telegramBot.executors.marketplace.items.settings.appliedFilters;

import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.ItemFilter;

import java.util.Collection;

public class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processMiddleInput(InputState.ITEMS_SHOW_SETTINGS_APPLIED_FILTER_ADD_OR_REMOVE);

        ItemFilter filter = botInnerService.getFilterFromDatabaseByUserInputCallback(updateInfo.getChatId());

        Collection<ItemFilter> appliedFilters = botInnerService.getItemShowSettingsForUser(updateInfo.getChatId()).getItemShowAppliedFilters();

        String text;
        if(appliedFilters.contains(filter)) {
            text = "Chosen filter is:\n" + filter + "\nWould you like to remove it?";
        }
        else{
            text = "Chosen filter is:\n" + filter + "\nWould you like to add it?";
        }

        askYesOrNoFromInlineKeyboard(text, Callbacks.INPUT_CALLBACK_TRUE, Callbacks.INPUT_CALLBACK_FALSE);
    }
}
