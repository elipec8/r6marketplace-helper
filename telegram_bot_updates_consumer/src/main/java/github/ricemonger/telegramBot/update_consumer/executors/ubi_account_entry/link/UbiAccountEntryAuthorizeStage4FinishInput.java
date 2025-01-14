package github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryAuthorizeStage4FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());

        sendText("""
                Credentials successfully provided. Be aware:
                2FA reauthorization is required after every login(when you enter your credentials) in Ubisoft log in page.
                """);
    }
}
