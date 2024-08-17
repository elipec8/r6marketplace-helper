package github.ricemonger.telegramBot.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.dtos.ItemFilter;

import java.util.ArrayList;
import java.util.List;

public class ItemsShowSettingsChangeAppliedFiltersStage1AskFilterNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.FILTER_NAME, InputGroup.ITEMS_SHOW_SETTING_APPLIED_FILTERS_CHANGE);

        List<String> appliedFilters =
                botInnerService.getUserItemShowSettings(updateInfo.getChatId()).getItemShowAppliedFilters().stream().map(ItemFilter::getName).toList();
        List<String> allFilters = new ArrayList<>(botInnerService.getAllUserItemFiltersNames(updateInfo.getChatId()));

        CallbackButton[] buttons = new CallbackButton[allFilters.size()];

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new CallbackButton(allFilters.get(i), Callbacks.INPUT_CALLBACK_PREFIX + allFilters.get(i));
        }

        String appliedFiltersString = String.join(", ", appliedFilters);
        String allFiltersString = String.join(", ", allFilters);

        askFromInlineKeyboard(
                "Current filters:\n"
                + appliedFiltersString
                + "\nAvailable filters:\n"
                + allFiltersString
                + "\nChoose filter to add or remove from current filters.",
                1,
                buttons);
    }
}
