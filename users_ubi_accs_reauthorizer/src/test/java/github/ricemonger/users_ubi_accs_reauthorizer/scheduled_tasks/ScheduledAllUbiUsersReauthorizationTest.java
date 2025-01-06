package github.ricemonger.users_ubi_accs_reauthorizer.scheduled_tasks;

import github.ricemonger.users_ubi_accs_reauthorizer.services.DTOs.UserToNotify;
import github.ricemonger.users_ubi_accs_reauthorizer.services.NotificationService;
import github.ricemonger.users_ubi_accs_reauthorizer.services.UserUbiAccountEntryService;
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
    private UserUbiAccountEntryService userUbiAccountEntryService;
    @MockBean
    private NotificationService telegramBotService;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailures_should_reauthorize_All_and_notify_via_services() {
        List<UserToNotify> toNotify = new ArrayList<>();
        toNotify.add(new UserToNotify(1L, "email"));
        when(userUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledAllUbiUsersReauthorization.reauthorizeAllUbiUsersAndNotifyAboutFailures();

        verify(userUbiAccountEntryService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotService).sendPrivateNotification(eq(1L), anyString());
    }
}