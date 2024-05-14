package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserEntity;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.neo4j.services.UbiUserService;
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
    private UbiUserService ubiUserService;

    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @Autowired
    private ScheduledUbiUsersReauthorization scheduledUbiUsersReauthorization;

    @Test
    public void reauthorizeUbiUsersAndNotifyAboutFailuresShouldReauthorizeAndNotifyViaServices() {
        List<UbiUserEntity> toNotify = new ArrayList<>();


        TelegramLinkedUserEntity telegramUserEntity = new TelegramLinkedUserEntity();
        telegramUserEntity.setChatId("chatId");

        UbiUserEntity ubiUserEntity = new UbiUserEntity();
        ubiUserEntity.setEmail("email");
        ubiUserEntity.setLinkedTelegramUser(telegramUserEntity);

        toNotify.add(ubiUserEntity);
        toNotify.add(ubiUserEntity);

        when(ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(ubiUserService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService, times(2)).notifyUserAboutUbiAuthorizationFailure(telegramUserEntity.getChatId(), ubiUserEntity.getEmail());
    }
}