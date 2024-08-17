package github.ricemonger.telegramBot.executors.credentials.link;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.UbiUserAuthorizationServerErrorException;

public class CredentialsAddPasswordInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand()
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        processLastInput("Credentials successfully provided.");

        botInnerService.addUserUbiAccountEntryByUserInput(updateInfo.getChatId());
    }
}
