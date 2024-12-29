package github.ricemonger.telegramBot.executors.credentials.unlink;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.unlink.UbiAccountEntryUnlinkConfirmedFinishCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryEntityDTOUnlinkConfirmedFinishCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_remove_account_entry_and_notify_user() {
        UbiAccountEntryUnlinkConfirmedFinishCallback ubiAccountEntryUnlinkConfirmedFinishCallback = new UbiAccountEntryUnlinkConfirmedFinishCallback();

        ubiAccountEntryUnlinkConfirmedFinishCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).removeUserUbiAccountEntry(MockUpdateInfos.UPDATE_INFO.getChatId());

        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }

}