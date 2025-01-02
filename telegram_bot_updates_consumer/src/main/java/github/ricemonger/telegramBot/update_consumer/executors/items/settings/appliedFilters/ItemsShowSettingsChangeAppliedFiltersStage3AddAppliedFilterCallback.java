package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeAppliedFiltersStage3AddAppliedFilterCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.addItemShowAppliedFilterByUserInput(updateInfo.getChatId());
        sendText("Filter successfully added.");
    }
}
