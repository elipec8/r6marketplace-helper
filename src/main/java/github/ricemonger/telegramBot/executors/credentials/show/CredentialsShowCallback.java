package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.UbiAccountEntryDoesntExistException;

public class CredentialsShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() throws UbiAccountEntryDoesntExistException {
        String text = "Your current Linked Ubisoft Account's Email:\n";

        String email = botInnerService.getUserUbiAccountEntryEmail(updateInfo.getChatId());

        sendText(text + email);
    }
}
