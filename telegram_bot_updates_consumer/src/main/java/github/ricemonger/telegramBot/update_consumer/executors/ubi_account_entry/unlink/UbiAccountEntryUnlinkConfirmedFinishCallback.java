package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.unlink;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryUnlinkConfirmedFinishCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        botInnerService.removeUserUbiAccountEntry(updateInfo.getChatId());
        sendText("Ubisoft Account successfully unlinked");
    }
}
