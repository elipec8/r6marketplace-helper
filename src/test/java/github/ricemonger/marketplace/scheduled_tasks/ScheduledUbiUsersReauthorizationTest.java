package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramUbiAccountService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;

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
        List<UbiAccount> unauthorizedUsers = new ArrayList<>();
        UbiAccount unauthorizedUser = new UbiAccount();
        //unauthorizedUser.setChatId("1");
        unauthorizedUser.setEmail("email");
        unauthorizedUsers.add(unauthorizedUser);

        //when(telegramUbiAccountService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(unauthorizedUsers);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(telegramUbiAccountService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService).notifyUserAboutUbiAuthorizationFailure("1", "email");
    }
}