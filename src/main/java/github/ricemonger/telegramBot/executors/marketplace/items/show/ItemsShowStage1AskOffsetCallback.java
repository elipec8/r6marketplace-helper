package github.ricemonger.telegramBot.executors.marketplace.items.show;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowStage1AskOffsetCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.ITEMS_SHOW_OFFSET, InputGroup.ITEM_SHOW, "Please enter items search offset:");
    }
}
