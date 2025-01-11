package github.ricemonger.users_ubi_accs_reauthorizer.scheduled_tasks;

import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserUnauthorizedUbiAccount;
import github.ricemonger.users_ubi_accs_reauthorizer.services.NotificationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.UserUbiAccountEntryAuthorizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScheduledAllUbiUsersReauthorizationTest {
    @Autowired
    private ScheduledAllUbiUsersReauthorization scheduledAllUbiUsersReauthorization;
    @MockBean
    private UserUbiAccountEntryAuthorizationService userUbiAccountEntryAuthorizationService;
    @MockBean
    private NotificationService telegramBotService;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailures_should_reauthorize_All_and_notify_via_services() {
        List<UserUnauthorizedUbiAccount> toNotify = new ArrayList<>();
        toNotify.add(new UserUnauthorizedUbiAccount(1L, "email"));
        when(userUbiAccountEntryAuthorizationService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledAllUbiUsersReauthorization.reauthorizeAllUbiUsersAndNotifyAboutFailures();

        verify(userUbiAccountEntryAuthorizationService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotService).sendAuthorizationNotification(eq(1L), anyString());
    }
}