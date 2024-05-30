package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.services.UbiUserPostgresRepositoryService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduledUbiUsersReauthorizationTests {

    @MockBean
    private UbiUserPostgresRepositoryService ubiUserService;

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @Autowired
    private ScheduledUbiUsersReauthorization scheduledUbiUsersReauthorization;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailuresShouldReauthorizeAndNotifyViaServices() {
        List<UbiUserEntity> toNotify = new ArrayList<>();


        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        telegramUserEntity.setChatId("chatId");

        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        //ubiUserEntity.setLinkedTelegramUser(telegramUserEntity);

        toNotify.add(ubiUserEntity);
        toNotify.add(ubiUserEntity);

        when(ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(ubiUserService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService, times(2)).notifyUserAboutUbiAuthorizationFailure(telegramUserEntity.getChatId(), ubiUserEntity.getEmail());
    }
}