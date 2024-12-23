package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth_two_fa_code;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeEnter2FACodeStage2ExceptionOrSuccessFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.reauthorizeUbiAccountEntryBy2FACode(updateInfo.getChatId());

        sendText("Your account has been successfully reauthorized.");
    }
}
