package github.ricemonger.telegramBot.update_consumer.listeners;
import github.ricemonger.telegramBot.update_consumer.executors.transfer.TransferDirect; // <--- ADICIONE ISSO

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.update_consumer.executors.ExecutorsService;
import github.ricemonger.telegramBot.update_consumer.executors.cancel.Cancel;
import github.ricemonger.telegramBot.update_consumer.executors.help.HelpDirect;
import github.ricemonger.telegramBot.update_consumer.executors.itemFilters.ItemFiltersDirect;
import github.ricemonger.telegramBot.update_consumer.executors.items.ItemsDirect;
import github.ricemonger.telegramBot.update_consumer.executors.notifications.NotificationsDirect;
import github.ricemonger.telegramBot.update_consumer.executors.start.StartDirect;
import github.ricemonger.telegramBot.update_consumer.executors.tradeManagers.TradeManagersDirect;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.UbiAccountEntryDirect;
import github.ricemonger.utils.exceptions.client.UnexpectedDirectCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static github.ricemonger.telegramBot.client.PublicBotCommands.*;

@Component
@RequiredArgsConstructor
public class DirectCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {

        String text = updateInfo.getMessageText();

        switch (text) {

            case START_COMMAND -> executorsService.execute(StartDirect.class, updateInfo);

            case HELP_COMMAND -> executorsService.execute(HelpDirect.class, updateInfo);

            case ITEMS_COMMAND -> executorsService.execute(ItemsDirect.class, updateInfo);

            case NOTIFICATIONS_COMMAND -> executorsService.execute(NotificationsDirect.class, updateInfo);

            case MANAGERS_COMMAND -> executorsService.execute(TradeManagersDirect.class, updateInfo);

            case FILTERS_COMMAND -> executorsService.execute(ItemFiltersDirect.class, updateInfo);

            case CREDENTIALS_COMMAND -> executorsService.execute(UbiAccountEntryDirect.class, updateInfo);

            case TRANSFER_COMMAND -> executorsService.execute(TransferDirect.class, updateInfo);

            case CANCEL_COMMAND -> executorsService.execute(Cancel.class, updateInfo);

            default -> throw new UnexpectedDirectCommandException("Unexpected message text value: " + text);
        }
    }
}
