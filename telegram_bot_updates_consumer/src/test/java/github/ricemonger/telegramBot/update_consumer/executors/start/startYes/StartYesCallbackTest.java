package github.ricemonger.telegramBot.update_consumer.executors.start.startYes;

import github.ricemonger.telegramBot.update_consumer.BotInnerService;
import github.ricemonger.telegramBot.update_consumer.executors.MockUpdateInfos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
class StartYesCallbackTest {

    @MockBean
    private BotInnerService botInnerService;

    @Test
    public void initAndExecute_should_check_if_user_is_registered_and_register_and_ask_to_add_credentials_if_not() {
        StartYesCallback startYesCallback = new StartYesCallback();
        when(botInnerService.isUserRegistered(MockUpdateInfos.UPDATE_INFO.getChatId())).thenReturn(false);

        startYesCallback.initAndExecute(MockUpdateInfos.UPDATE_INFO, botInnerService);

        verify(botInnerService).registerUser(MockUpdateInfos.UPDATE_INFO.getChatId());
        verify(botInnerService).askFromInlineKeyboard(same(MockUpdateInfos.UPDATE_INFO), anyString(), anyInt(), any());
    }
}