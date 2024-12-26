package github.ricemonger.telegramBot.executors.credentials.show;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.ubi_account_entry.show.UbiAccountEntryShowCallback;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiAccountEntryEntityDTOShowCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_send_text() {
        UbiAccountEntryShowCallback ubiAccountEntryShowCallback = new UbiAccountEntryShowCallback();

        ubiAccountEntryShowCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).getUserUbiAccountEntryEmail(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(eq(MockUpdateInfos.UPDATE_INFO), anyString());
    }

    @Test
    public void initAndExecute_should_throw_exception_to_next_method_if_exception_was_thrown() {
        UbiAccountEntryShowCallback ubiAccountEntryShowCallback = new UbiAccountEntryShowCallback();

        when(botInnerService.getUserUbiAccountEntryEmail(MockUpdateInfos.UPDATE_INFO.getChatId())).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> {
            ubiAccountEntryShowCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);
        });
    }
}