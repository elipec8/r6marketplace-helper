package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth_two_fa_code;

import github.ricemonger.telegramBot.InputGroup;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Please enter your current Ubisoft 2FA code:");
        processFirstInput(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE);
    }
}
