package github.ricemonger.telegramBot.executors.marketplace.speculative.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class SpeculativeItemsShowOwnedStartCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.CREDENTIALS_FULL_OR_EMAIL, InputGroup.SPECULATIVE_ITEMS_SHOW_OWNED, "Please choose used credential's email:");
    }
}
