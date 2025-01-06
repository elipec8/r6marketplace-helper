package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.reauth;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;

public class UbiAccountEntryReauthorizeEnter2FACodeStage1Ask2FACodeCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        sendText("Please enter your current Ubisoft 2FA code:");
        processFirstInput(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, InputGroup.UBI_ACCOUNT_ENTRY_REAUTHORIZE_2FA_CODE);
    }
}
