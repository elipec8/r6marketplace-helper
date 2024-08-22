package github.ricemonger.telegramBot.executors.items.settings.appliedFilters;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class ItemsShowSettingsChangeAppliedFiltersStage3FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.updateUserItemShowAppliedFiltersSettingsByUserInput(updateInfo.getChatId());

        sendText("Filter's status changed successfully.");
    }
}
