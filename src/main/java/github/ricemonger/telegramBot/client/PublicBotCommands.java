package github.ricemonger.telegramBot.client;


import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public class PublicBotCommands {

    public final static List<BotCommand> botCommands = List.of(
            new BotCommand("/start", "Start chatting with bot"),
            new BotCommand("/help", "Get help with bot interactions"),
            new BotCommand("/items","Search for items on Marketplace"),
            new BotCommand("/trades","Search for your ongoing or finished trades on Marketplace"),
            new BotCommand("/tradeManagers","Operate on your trade managers"),
            new BotCommand("/itemFilters","Manage your item filters"),
            new BotCommand("/credentials", "Manage your linked Ubisoft account"),
            new BotCommand("/cancel", "Cancels current operation")
    );

    public static String getHelpText() {
        return """
                /start - Start chatting with bot
                /help - Get help with bot interactions
                /items - Search for items on Marketplace
                    You can look for items on Marketplace by applied to search filters.
                    Sorting options for the items and starting offset can be chosen before search.
                    Item properties to be shown, number of items per one message and
                    maximum amount of messages from one request can be specified too.
                /trades - Search for your ongoing or finished Marketplace trades
                    New trades can be created or existing ones can be updated.
                    You can look for trades by applied to search filters.
                    Sorting options for the trades and starting offset can be chosen before search.
                /tradeManagers - Operate your trade managers
                    Trade Managers are used to automatically operate your trades on Marketplace.
                    They operate on specific items either by item ID or item filters.
                    You can create, update, remove, enable or disable your trade managers.
                /itemFilters - Manage your item filters
                    Item Filters are used to search for items by specific item properties on Marketplace.
                    You can create, update or remove your item filters.
                /credentials - Manage your linked Ubisoft account
                    You can link or unlink your Ubisoft account to your Telegram account.
                    Due to need to directly log in to Marketplace, your Ubisoft account email and password
                    are required to be stored in the bot's database. Password is stored in encrypted form.
                    One Ubisoft account can be linked to many Telegram accounts.
                    Each Telegram account can be linked to only one Ubisoft account.
                    Linked Ubisoft account is used to operate on your trades on Marketplace.
                /cancel - Cancels current operation
                """;
    }
}
