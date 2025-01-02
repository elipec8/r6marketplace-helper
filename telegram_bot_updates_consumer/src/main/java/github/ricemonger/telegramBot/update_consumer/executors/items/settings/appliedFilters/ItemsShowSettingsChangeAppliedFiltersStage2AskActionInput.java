package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.DTOs.personal.ItemFilter;

import java.util.Collection;

public class ItemsShowSettingsChangeAppliedFiltersStage2AskActionInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        ItemFilter filter = botInnerService.getUserItemFilterByUserCurrentInputCallbackFilterName(updateInfo);

        Collection<String> appliedFilters = botInnerService.getItemShowAppliedFiltersNames(updateInfo.getChatId());

        if (appliedFilters.contains(filter.getName())) {
            String text = "Chosen filter is:\n" + filter.toHandsomeString() + "\nWould you like to remove it?";
            askYesOrNoFromInlineKeyboard(text, Callbacks.DELETE_ITEM_SHOW_APPLIED_FILTER, Callbacks.CANCEL);
        } else {
            String text = "Chosen filter is:\n" + filter.toHandsomeString() + "\nWould you like to add it?";
            askYesOrNoFromInlineKeyboard(text, Callbacks.ADD_ITEM_SHOW_APPLIED_FILTER, Callbacks.CANCEL);
        }
    }
}
