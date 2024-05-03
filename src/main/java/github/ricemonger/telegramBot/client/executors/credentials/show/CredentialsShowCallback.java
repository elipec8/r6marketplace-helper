package github.ricemonger.telegramBot.client.executors.credentials.show;

import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

import java.util.List;

public class CredentialsShowCallback extends AbstractBotCommandExecutor {
    @Override
    protected void executeCommand() {
        StringBuilder sb = new StringBuilder("Here are your credentials:\n\n");

        List<String> emails = botInnerService.getCredentialsEmailsList(updateInfo.getChatId());

        for(String email : emails){
            sb.append(email).append("\n");
        }

        sendText(sb.toString());
    }
}
