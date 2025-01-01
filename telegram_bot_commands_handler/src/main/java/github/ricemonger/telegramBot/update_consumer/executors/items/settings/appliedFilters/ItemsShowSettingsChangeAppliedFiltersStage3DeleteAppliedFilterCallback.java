package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeAppliedFiltersStage3DeleteAppliedFilterCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.deleteItemShowAppliedFilterByUserInput(updateInfo.getChatId());
        sendText("Filter successfully removed.");
    }
}
