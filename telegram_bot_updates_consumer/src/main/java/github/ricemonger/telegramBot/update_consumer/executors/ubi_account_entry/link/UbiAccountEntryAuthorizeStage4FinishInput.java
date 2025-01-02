package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryAuthorizeStage4FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());

        sendText("""
                Credentials successfully provided. Be aware:
                Do not check "Remember this device" box on the Ubisoft 2FA login page, as it will cause the bot to fail to login.
                """);
    }
}
