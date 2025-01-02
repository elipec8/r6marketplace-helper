package github.ricemonger.telegramBot.update_consumer.executors.start.startYes;

import github.ricemonger.telegramBot.Callbacks;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class StartYesCallback extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        botInnerService.registerUser(updateInfo.getChatId());
        askYesOrNoFromInlineKeyboard(
                "You were successfully registered, would you like to add your first Ubisoft Credentials?",
                Callbacks.UBI_ACCOUNT_ENTRY_LINK,
                Callbacks.CANCEL_SILENT);
    }
}
