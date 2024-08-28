package github.ricemonger.telegramBot.client;


import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public class PublicBotCommands {

    public static final String START_COMMAND = "/start";
    public static final String HELP_COMMAND = "/help";
    public static final String ITEMS_COMMAND = "/items";
    public static final String TRADES_COMMAND = "/trades";
    public static final String MANAGERS_COMMAND = "/managers";
    public static final String FILTERS_COMMAND = "/filters";
    public static final String CREDENTIALS_COMMAND = "/credentials";
    public static final String CANCEL_COMMAND = "/cancel";

    public final static List<BotCommand> botCommands = List.of(
            new BotCommand(START_COMMAND, "Start chatting with bot"),
            new BotCommand(HELP_COMMAND, "Get help with bot interactions"),
            new BotCommand(ITEMS_COMMAND, "Search for items on Marketplace"),
            new BotCommand(TRADES_COMMAND, "Search for your trades on Marketplace"),
            new BotCommand(MANAGERS_COMMAND, "Operate on your trade managers"),
            new BotCommand(FILTERS_COMMAND, "Manage your item filters"),
            new BotCommand(CREDENTIALS_COMMAND, "Manage your linked Ubisoft account"),
            new BotCommand(CANCEL_COMMAND, "Cancels current operation")
    );

    public static String getHelpText() {
        StringBuilder sb = new StringBuilder();
        sb.append(START_COMMAND).append(" - Start chatting with bot\n")
                .append(HELP_COMMAND).append(" - Get help with bot interactions\n")
                .append(ITEMS_COMMAND).append(" - Search for items on Marketplace\n")
                .append("""
                        You can look for items on Marketplace by applied to search filters.
                        Sorting options for the items and starting offset can be chosen before search.
                        Item properties to be shown, number of items per one message and
                        maximum amount of messages from one request can be specified too.
                        """)
                .append(TRADES_COMMAND).append(" - Search for your ongoing or finished Marketplace trades\n")
                .append("""
                        New trades can be created or existing ones can be updated.
                        You can look for trades by applied to search filters.
                        Sorting options for the trades and starting offset can be chosen before search.
                        """)
                .append(MANAGERS_COMMAND).append(" - Operate your trade managers\n")
                .append("""
                        Trade Managers are used to automatically operate your trades on Marketplace.
                        They operate on specific items either by item ID or item filters.
                        You can create, update, remove, enable or disable your trade managers.
                        """)
                .append(FILTERS_COMMAND).append(" - Manage your item filters\n")
                .append("""
                        Item Filters are used to search for items by specific item properties on Marketplace.
                        You can create, update or remove your item filters.
                        """)
                .append(CREDENTIALS_COMMAND).append(" - Manage your linked Ubisoft account\n")
                .append("""
                        You can link or unlink your Ubisoft account to your Telegram account.
                        Due to need to directly log in to Marketplace, your Ubisoft account email and password
                        are required to be stored in the bot 's database. Password is stored in encrypted form.
                        One Ubisoft account can be linked to many Telegram accounts.
                        Each Telegram account can be linked to only one Ubisoft account.
                        Linked Ubisoft account is used to operate on your trades on Marketplace.
                        """)
                .append(CANCEL_COMMAND).append(" - Cancels current operation\n");
        return sb.toString();
    }
}
