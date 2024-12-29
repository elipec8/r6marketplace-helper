package github.ricemonger.telegramBot.update_consumer.executors.help;

import github.ricemonger.telegramBot.client.PublicBotCommands;
import github.ricemonger.telegramBot.update_consumer.executors.AbstractBotCommandExecutor;

public class HelpDirect extends AbstractBotCommandExecutor {

    @Override
    protected void executeCommand() {
        sendText(PublicBotCommands.getHelpText());
    }
}
