package github.ricemonger.telegramBot.update_consumer.executors.itemFilters.showOrRemove;

import github.ricemonger.telegramBot.CallbackButton;
import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

import java.util.ArrayList;
import java.util.List;

public class FiltersShowAllNamesStage1AskNameCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        List<String> filterNames = new ArrayList<>(botInnerService.getAllUserItemFiltersNames(updateInfo.getChatId()));

        if (filterNames.isEmpty()) {
            sendText("You have no filters saved");
        } else {
            processFirstInput(InputState.ITEM_FILTER_NAME, InputGroup.ITEM_FILTER_SHOW_OR_REMOVE);

            CallbackButton[] buttons = new CallbackButton[filterNames.size()];
            for (int i = 0; i < filterNames.size(); i++) {
                String name = filterNames.get(i);
                buttons[i] = new CallbackButton(name, Callbacks.INPUT_CALLBACK_PREFIX + name);
            }

            askFromInlineKeyboard("Please choose filter to show:", 2, buttons);
        }
    }
}
