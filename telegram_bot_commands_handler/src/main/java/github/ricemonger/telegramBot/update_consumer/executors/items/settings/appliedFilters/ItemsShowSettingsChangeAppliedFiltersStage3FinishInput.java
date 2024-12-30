package github.ricemonger.telegramBot.update_consumer.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeAppliedFiltersStage3FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(updateInfo.getChatId());

        sendText("Filter's status changed successfully.");
    }
}
