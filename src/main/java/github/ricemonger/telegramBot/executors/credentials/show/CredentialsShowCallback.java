package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.executors.AbstractBotCommandExecutor;

import java.util.List;

public class CredentialsShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        StringBuilder sb = new StringBuilder("Here are your credentials:\n\n");

        List<String> emails = botInnerService.getUserCredentialsEmail(updateInfo.getChatId());

        for (String email : emails) {
            sb.append(email).append("\n");
        }

        sendText(sb.toString());
    }
}
