package github.ricemonger.telegramBot.executors.credentials.unlink;

import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.ubi_account_entry.unlink.UbiAccountEntryUnlinkRequestCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryEntityDTOUnlinkRequestCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_ask_from_keyboard() {
        UbiAccountEntryUnlinkRequestCallback ubiAccountEntryUnlinkRequestCallback = new UbiAccountEntryUnlinkRequestCallback();

        ubiAccountEntryUnlinkRequestCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}