package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.help.HelpDirect;
import github.ricemonger.telegramBot.executors.itemFilters.ItemFiltersDirect;
import github.ricemonger.telegramBot.executors.items.ItemsDirect;
import github.ricemonger.telegramBot.executors.start.StartDirect;
import github.ricemonger.telegramBot.executors.tradeManagers.TradeManagersDirect;
import github.ricemonger.telegramBot.executors.trades.TradesDirect;
import github.ricemonger.telegramBot.executors.ubi_account_entry.UbiAccountEntryDirect;
import github.ricemonger.utils.exceptions.UnexpectedDirectCommandException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DirectCommandListenerTest {
    @Autowired
    private DirectCommandListener directCommandListener;
    @MockBean
    private ExecutorsService executorsService;

    @Test
    public void handleUpdate_should_start() {
        directCommandListener.handleUpdate(updateInfo("/start"));

        verify(executorsService).execute(StartDirect.class, updateInfo("/start"));
    }

    @Test
    public void handleUpdate_should_help() {
        directCommandListener.handleUpdate(updateInfo("/help"));

        verify(executorsService).execute(HelpDirect.class, updateInfo("/help"));
    }

    @Test
    public void handleUpdate_should_items() {
        directCommandListener.handleUpdate(updateInfo("/items"));

        verify(executorsService).execute(ItemsDirect.class, updateInfo("/items"));
    }

    @Test
    public void handleUpdate_should_trades() {
        directCommandListener.handleUpdate(updateInfo("/trades"));

        verify(executorsService).execute(TradesDirect.class, updateInfo("/trades"));
    }

    @Test
    public void handleUpdate_should_tradeManagers() {
        directCommandListener.handleUpdate(updateInfo("/tradeManagers"));

        verify(executorsService).execute(TradeManagersDirect.class, updateInfo("/tradeManagers"));
    }

    @Test
    public void handleUpdate_should_itemFilters() {
        directCommandListener.handleUpdate(updateInfo("/itemFilters"));

        verify(executorsService).execute(ItemFiltersDirect.class, updateInfo("/itemFilters"));
    }

    @Test
    public void handleUpdate_should_credentials() {
        directCommandListener.handleUpdate(updateInfo("/credentials"));

        verify(executorsService).execute(UbiAccountEntryDirect.class, updateInfo("/credentials"));
    }

    @Test
    public void handleUpdate_should_cancel() {
        directCommandListener.handleUpdate(updateInfo("/cancel"));

        verify(executorsService).execute(Cancel.class, updateInfo("/cancel"));
    }

    @Test
    public void handleUpdate_should_throw() {
        assertThrows(UnexpectedDirectCommandException.class, () -> {
            directCommandListener.handleUpdate(updateInfo("invalid"));
        });
    }

    private UpdateInfo updateInfo(String command) {
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.setMessageText(command);
        return updateInfo;
    }
}