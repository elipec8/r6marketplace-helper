package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.ubi_account_entry.UbiAccountEntryDirect;
import github.ricemonger.telegramBot.executors.help.HelpDirect;
import github.ricemonger.telegramBot.executors.itemFilters.ItemFiltersDirect;
import github.ricemonger.telegramBot.executors.items.ItemsDirect;
import github.ricemonger.telegramBot.executors.start.StartDirect;
import github.ricemonger.telegramBot.executors.tradeManagers.TradeManagersDirect;
import github.ricemonger.telegramBot.executors.trades.TradesDirect;
import github.ricemonger.utils.exceptions.UnexpectedDirectCommandException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DirectCommandListener {

    private final ExecutorsService executorsService;

    public void handleUpdate(UpdateInfo updateInfo) {

        String text = updateInfo.getMessageText();

        switch (text) {

            case "/start" -> executorsService.execute(StartDirect.class, updateInfo);

            case "/help" -> executorsService.execute(HelpDirect.class, updateInfo);

            case "/items" -> executorsService.execute(ItemsDirect.class, updateInfo);

            case "/trades" -> executorsService.execute(TradesDirect.class, updateInfo);

            case "/tradeManagers" -> executorsService.execute(TradeManagersDirect.class, updateInfo);

            case "/itemFilters" -> executorsService.execute(ItemFiltersDirect.class, updateInfo);

            case "/credentials" -> executorsService.execute(UbiAccountEntryDirect.class, updateInfo);

            case "/cancel" -> executorsService.execute(Cancel.class, updateInfo);

            default -> throw new UnexpectedDirectCommandException("Unexpected message text value: " + text);
        }
    }
}
