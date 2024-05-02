package github.ricemonger.telegramBot.client.executors.credentials;

import github.ricemonger.telegramBot.client.BotService;
import github.ricemonger.telegramBot.client.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CredentialsDirectTests {

    @MockBean
    private BotService botService;

    @Test
    public void initAndExecuteShouldCheckIfUserIsRegisteredAndProposeToRegisterIfNot() {
        CredentialsDirect credentialsDirect = new CredentialsDirect();
        when(botService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        credentialsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botService).sendText(same(MockUpdateInfos.UPDATE_INFO), anyString());
        verify(botService,never()).askFromInlineKeyboard(any(), any(), anyInt(),any());
    }

    @Test
    public void initAndExecuteShouldAskToChooseTheActionIfUserIsRegistered() {
        CredentialsDirect credentialsDirect = new CredentialsDirect();
        when(botService.isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(true);

        credentialsDirect.initAndExecute(MockUpdateInfos.UPDATE_INFO, botService);

        verify(botService).isRegistered(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(),any());
    }

}