package github.ricemonger.telegramBot.client;


import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public class PublicBotCommands {

    public final static List<BotCommand> botCommands = List.of(
            new BotCommand("/start", "Start the bot"),
            new BotCommand("/help", "Get help"),
            new BotCommand("/credentials", "credentials operations"),
            new BotCommand("/marketplace", "marketplace operations")
    );

    public static String getHelpText() {
        return """
                /start - Start the bot
                /help - Get help
                /credentials - credentials operations
                """;
    }
}
