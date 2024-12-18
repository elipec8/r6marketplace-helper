package github.ricemonger.telegramBot.executors.credentials;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.ubi_account_entry.UbiAccountEntryDirect;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UbiAccountEntryEntityDTODirectTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_check_if_user_is_registered_and_propose_to_register_if_not() {
        UbiAccountEntryDirect ubiAccountEntryDirect = new UbiAccountEntryDirect();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        ubiAccountEntryDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService, never()).askFromInlineKeyboard(any(), any(), anyInt(), any());
    }

    @Test
    public void initAndExecute_should_ask_to_choose_the_action_if_user_is_registered() {
        UbiAccountEntryDirect ubiAccountEntryDirect = new UbiAccountEntryDirect();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        ubiAccountEntryDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService, never()).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}