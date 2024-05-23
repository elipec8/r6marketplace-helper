package github.ricemonger.marketplace.scheduled_tasks;

import github.ricemonger.marketplace.databases.neo4j.entities.TelegramLinkedUserNode;
import github.ricemonger.marketplace.databases.neo4j.entities.UbiUserNode;
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
        List<UbiUserNode> toNotify = new ArrayList<>();


        TelegramLinkedUserNode telegramUserEntity = new TelegramLinkedUserNode();
        telegramUserEntity.setChatId("chatId");

        UbiUserNode ubiUserNode = new UbiUserNode();
        ubiUserNode.setEmail("email");
        ubiUserNode.setLinkedTelegramUser(telegramUserEntity);

        toNotify.add(ubiUserNode);
        toNotify.add(ubiUserNode);

        when(ubiUserService.reauthorizeAllUbiUsersAndGetUnauthorizedList()).thenReturn(toNotify);

        scheduledUbiUsersReauthorization.reauthorizeUbiUsersAndNotifyAboutFailures();

        verify(ubiUserService).reauthorizeAllUbiUsersAndGetUnauthorizedList();

        verify(telegramBotClientService, times(2)).notifyUserAboutUbiAuthorizationFailure(telegramUserEntity.getChatId(), ubiUserNode.getEmail());
    }
}