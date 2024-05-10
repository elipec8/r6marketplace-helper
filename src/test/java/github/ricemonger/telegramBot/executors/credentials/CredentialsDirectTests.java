package github.ricemonger.telegramBot.executors.credentials;

import github.ricemonger.telegramBot.client.BotInnerService;
import github.ricemonger.telegramBot.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CredentialsDirectTests {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecuteShouldCheckIfUserIsRegisteredAndProposeToRegisterIfNot() {
        CredentialsDirect credentialsDirect = new CredentialsDirect();
        when(botInnerService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        credentialsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botInnerService,never()).askFromInlineKeyboard(any(), any(), anyInt(),any());
    }

    @Test
    public void initAndExecuteShouldAskToChooseTheActionIfUserIsRegistered() {
        CredentialsDirect credentialsDirect = new CredentialsDirect();
        when(botInnerService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        credentialsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(),any());
    }

}