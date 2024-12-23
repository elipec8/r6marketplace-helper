package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class UbiAccountEntryAuthorizeStage4FinishInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        processLastInput();

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());

        sendText("Credentials successfully provided.");
    }
}
