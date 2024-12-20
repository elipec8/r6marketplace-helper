package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramUserUbiAccountEntryService;
import github.ricemonger.telegramBot.TelegramBotService;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntry;
import github.ricemonger.utils.DTOs.personal.UbiAccountAuthorizationEntryWithTelegram;
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

    @MockBean
    private TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;

    @MockBean
    private TelegramBotService telegramBotService;

    @Autowired
    private ScheduledAllUbiUsersReauthorization scheduledAllUbiUsersReauthorization;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailures_should_reauthorize_All_and_notify_via_services() {
        List<UbiAccountAuthorizationEntryWithTelegram> toNotify = new ArrayList<>();
        toNotify.add(new UbiAccountAuthorizationEntryWithTelegram("chatId", true, new UbiAccountAuthorizationEntry(
                "ubiProfileId",
                "email",
                "password",
                "ubiSessionId",
                "ubiSpaceId",
                "ubiAuthTicket",
                "ubiRememberDeviceTicket",
                "ubiRememberMeTicket")));
        when(telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledAllUbiUsersReauthorization.reauthorizeAllUbiUsersAndNotifyAboutFailures();

        verify(telegramUserUbiAccountEntryService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotService).sendNotificationToUser(eq("chatId"), anyString());
    }
}