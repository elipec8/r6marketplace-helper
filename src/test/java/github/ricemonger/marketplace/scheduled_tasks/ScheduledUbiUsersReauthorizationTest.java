package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramUbiAccountService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledUbiUsersReauthorizationTest {

    @MockBean
    private TelegramUbiAccountService telegramUbiAccountService;

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @Autowired
    private ScheduledUbiUsersReauthorization scheduledUbiUsersReauthorization;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailures_should_reauthorize_and_notify_via_services() {
        List<UbiAccountWithTelegram> toNotify = new ArrayList<>();
        toNotify.add(new UbiAccountWithTelegram("chatId", new UbiAccount("email", null, null, null, null, null, null, null, null, null)));
        when(telegramUbiAccountService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(telegramUbiAccountService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService).notifyUserAboutUbiAuthorizationFailure("chatId", "email");
    }
}