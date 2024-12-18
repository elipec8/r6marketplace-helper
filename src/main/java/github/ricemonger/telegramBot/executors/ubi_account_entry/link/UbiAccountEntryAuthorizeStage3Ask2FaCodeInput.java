package github.ricemonger.telegramBot.executors.ubi_account_entry.link;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.UbiUserAuthorizationClientErrorException;
import github.ricemonger.utils.exceptions.server.UbiUserAuthorizationServerErrorException;

public class UbiAccountEntryAuthorizeStage3Ask2FaCodeInput extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand()
            throws TelegramUserDoesntExistException,
            UbiUserAuthorizationClientErrorException,
            UbiUserAuthorizationServerErrorException {
        processMiddleInput(InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "Please provide your current Ubisoft 2FA code:");
    }
}
