package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.reauth_two_fa_code;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeEnter2FACodeStage2ExceptionOrSuccessFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.reauthorizeUbiAccountEntryBy2FACode(updateInfo.getChatId());

        sendText("""
                2FA Code was successfully provided. Be aware:
                Do not check "Remember this device" box on the Ubisoft 2FA login page, as it will cause the bot to fail to login.
                """);
    }
}
