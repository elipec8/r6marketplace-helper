package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;

public class UbiAccountEntryLinkStage2AskPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand()
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        processLastInput();

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());

        sendText("Credentials successfully provided.");
    }
}
