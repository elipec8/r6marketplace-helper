package github.ricemonger.telegramBot.executors.items.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class ItemsShowStage1AskOffsetCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.ITEMS_SHOW_OFFSET, InputGroup.ITEMS_SHOW, "Please enter items search offset:");
    }
}
