package github.ricemonger.telegramBot.executors.ubi_account_entry.reauth;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import github.ricemonger.telegramBot.executors.ubi_account_entry.UbiAccountEntryDirect;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UbiAccountEntryEntityDTOReauthorizeCallbackTest {
    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_check_if_user_is_registered_and_propose_to_register_if_not() {
        UbiAccountEntryReauthorizeCallback executor = new UbiAccountEntryReauthorizeCallback();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService, never()).askFromInlineKeyboard(any(), any(), anyInt(), any());
    }

    @Test
    public void initAndExecute_should_ask_to_choose_the_action_if_user_is_registered() {
        UbiAccountEntryReauthorizeCallback executor = new UbiAccountEntryReauthorizeCallback();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        executor.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService, never()).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}