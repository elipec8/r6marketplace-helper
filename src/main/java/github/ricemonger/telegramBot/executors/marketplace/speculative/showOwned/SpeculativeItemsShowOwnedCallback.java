package github.ricemonger.telegramBot.executors.marketplace.speculative.showOwned;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;

public class SpeculativeItemsShowOwnedCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processFirstInput(InputState.CREDENTIALS_FULL_OR_EMAIL, InputGroup.SPECULATIVE_ITEMS_SHOW_OWNED, "Please choose used credential's email:");
    }
}
