package github.ricemonger.telegramBot.executors.marketplace.filters.showOrRemove;

import github.ricemonger.telegramBot.client.CallbackButton;
import github.ricemonger.telegramBot.client.Callbacks;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

import java.util.ArrayList;
import java.util.List;

public class FiltersShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.FILTER_NAME, InputGroup.FILTERS_SHOW_REMOVE);

        List<String> filterNames = new ArrayList<>(botInnerService.getAllFilterNamesForUser(updateInfo.getChatId()));

        CallbackButton[] buttons = new CallbackButton[filterNames.size()];
        for (int i = 0; i < filterNames.size(); i++) {
            String name = filterNames.get(i);
            buttons[i] = new CallbackButton(name, Callbacks.INPUT_CALLBACK_PREFIX + name);
        }

        askFromInlineKeyboard("Please choose filter to show:", 2, buttons);
    }
}
