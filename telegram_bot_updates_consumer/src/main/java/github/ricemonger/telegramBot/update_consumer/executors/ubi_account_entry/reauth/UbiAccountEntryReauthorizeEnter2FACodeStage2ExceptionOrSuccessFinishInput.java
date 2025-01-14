package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.reauth;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryReauthorizeEnter2FACodeStage2ExceptionOrSuccessFinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.reauthorizeUbiAccountEntryBy2FACode(updateInfo.getChatId());

        sendText("""
                2FA Code was successfully provided. Be aware:
                2FA reauthorization is required after every login(when you enter your credentials) in Ubisoft log in page.
                """);
    }
}
