package github.ricemonger.telegramBot.updateReceiver.listeners;

import github.ricemonger.telegramBot.UpdateInfo;
import github.ricemonger.telegramBot.executors.ExecutorsService;
import github.ricemonger.telegramBot.executors.cancel.Cancel;
import github.ricemonger.telegramBot.executors.help.HelpDirect;
import github.ricemonger.telegramBot.executors.itemFilters.ItemFiltersDirect;
import github.ricemonger.telegramBot.executors.items.ItemsDirect;
import github.ricemonger.telegramBot.executors.start.StartDirect;
import github.ricemonger.telegramBot.executors.tradeManagers.TradeManagersDirect;
import github.ricemonger.telegramBot.executors.ubi_account_entry.UbiAccountEntryDirect;
import github.ricemonger.utils.exceptions.client.UnexpectedDirectCommandException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static github.ricemonger.telegramBot.client.PublicBotCommands.*;
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
        directCommandListener.handleUpdate(updateInfo(START_COMMAND));

        verify(executorsService).execute(StartDirect.class, updateInfo(START_COMMAND));
    }

    @Test
    public void handleUpdate_should_help() {
        directCommandListener.handleUpdate(updateInfo(HELP_COMMAND));

        verify(executorsService).execute(HelpDirect.class, updateInfo(HELP_COMMAND));
    }

    @Test
    public void handleUpdate_should_items() {
        directCommandListener.handleUpdate(updateInfo(ITEMS_COMMAND));

        verify(executorsService).execute(ItemsDirect.class, updateInfo(ITEMS_COMMAND));
    }

    @Test
    public void handleUpdate_should_tradeManagers() {
        directCommandListener.handleUpdate(updateInfo(MANAGERS_COMMAND));

        verify(executorsService).execute(TradeManagersDirect.class, updateInfo(MANAGERS_COMMAND));
    }

    @Test
    public void handleUpdate_should_itemFilters() {
        directCommandListener.handleUpdate(updateInfo(FILTERS_COMMAND));

        verify(executorsService).execute(ItemFiltersDirect.class, updateInfo(FILTERS_COMMAND));
    }

    @Test
    public void handleUpdate_should_credentials() {
        directCommandListener.handleUpdate(updateInfo(CREDENTIALS_COMMAND));

        verify(executorsService).execute(UbiAccountEntryDirect.class, updateInfo(CREDENTIALS_COMMAND));
    }

    @Test
    public void handleUpdate_should_cancel() {
        directCommandListener.handleUpdate(updateInfo(CANCEL_COMMAND));

        verify(executorsService).execute(Cancel.class, updateInfo(CANCEL_COMMAND));
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