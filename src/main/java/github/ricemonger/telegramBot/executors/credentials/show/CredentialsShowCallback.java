package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

public class CredentialsShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        String text = "Here is your current ubi account email:\n\n";

        String email = botInnerService.getUserUbiAccountEntryEmail(updateInfo.getChatId());

        sendText(email == null ? "You haven't set your ubi account yet" : text + "Email: " + email);
    }
}
