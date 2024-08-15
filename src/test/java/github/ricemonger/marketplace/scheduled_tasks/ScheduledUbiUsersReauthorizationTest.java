package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.services.TelegramUserUbiAccountEntryService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import github.ricemonger.utils.dtos.UbiAccountEntry;
import github.ricemonger.utils.dtos.UbiAccountWithTelegram;
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
class ScheduledUbiUsersReauthorizationTest {

    @MockBean
    private TelegramUserUbiAccountEntryService telegramUserUbiAccountEntryService;

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @Autowired
    private ScheduledUbiUsersReauthorization scheduledUbiUsersReauthorization;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailures_should_reauthorize_and_notify_via_services() {
        List<UbiAccountWithTelegram> toNotify = new ArrayList<>();
        toNotify.add(new UbiAccountWithTelegram("chatId", new UbiAccountEntry("email", null, null, null, null, null, null, null, null, null)));
        when(telegramUserUbiAccountEntryService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(telegramUserUbiAccountEntryService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService).sendText(eq("chatId"), anyString());
    }
}