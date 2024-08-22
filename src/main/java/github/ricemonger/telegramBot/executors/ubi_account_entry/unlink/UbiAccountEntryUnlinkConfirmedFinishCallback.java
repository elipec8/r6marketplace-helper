package github.ricemonger.telegramBot.executors.ubi_account_entry.unlink;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryUnlinkConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserUbiAccountEntry(updateInfo.getChatId());
    }
}
