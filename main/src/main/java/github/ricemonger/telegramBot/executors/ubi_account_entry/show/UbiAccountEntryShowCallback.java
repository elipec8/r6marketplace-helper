package github.ricemonger.telegramBot.executors.ubi_account_entry.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;
import github.ricemonger.utils.exceptions.client.UbiAccountEntryDoesntExistException;

public class UbiAccountEntryShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() throws UbiAccountEntryDoesntExistException {
        String text = "Your current Linked Ubisoft Account's Email:\n";

        String email = botInnerService.getUserUbiAccountEntryEmail(updateInfo.getChatId());

        sendText(text + email);
    }
}
