package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth.two_fa_code;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Please enter the 2FA code you received from Ubisoft:");
        processFirstInput(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE);
    }
}
