package github.ricemonger.telegramBot.client.executors.help;

import github.ricemonger.telegramBot.client.PublicBotCommands;
import github.ricemonger.telegramBot.client.executors.AbstractBotCommandExecutor;

public class HelpDirect extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        sendText(PublicBotCommands.getHelpText());
    }
}
