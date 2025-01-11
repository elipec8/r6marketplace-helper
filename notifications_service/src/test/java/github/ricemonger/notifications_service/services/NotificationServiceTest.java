package github.ricemonger.notifications_service.services;

import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import github.ricemonger.notifications_service.services.abstraction.UserDatabaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @MockBean
    private UserDatabaseService userDatabaseService;
    @MockBean
    private TelegramBotClientService telegramBotClientService;

    @Test
    public void sendPrivateNotificationToUser_should_send_if_user_has_true_private_flag_false_public() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId",
                true,
                false,
                false,
                false,
                false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService).sendText("chatId", "text");
    }

    @Test
    public void sendPrivateNotificationToUser_should_not_send_if_user_has_false_private_flag_true_public() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", false, true,
                false,
                false,
                false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPrivateNotificationToUser_should_not_send_if_user_has_null_private_flag_true_public() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", null, true,
                false,
                false,
                false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_send_if_user_has_true_public_Notification_flag_false_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", false, true,
                false,
                false,
                false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_not_send_if_user_has_false_public_Notification_flag_true_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", true, false,
                false,
                false,
                false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_not_send_if_user_has_null_public_Notification_flag_true_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", true, null,
                null,
                null,
                null);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicNotificationToAllUsers_should_send_to_expected_users() {
        ToBeNotifiedUser user1 = new ToBeNotifiedUser("chatId1", false, true, null, null, null);
        ToBeNotifiedUser user2 = new ToBeNotifiedUser("chatId2", null, true, null, null, null);
        ToBeNotifiedUser user3 = new ToBeNotifiedUser("chatId3", true, false, null, null, null);
        ToBeNotifiedUser user4 = new ToBeNotifiedUser("chatId4", true, null, null, null, null);

        when(userDatabaseService.getAllToBeNotifiedUsers()).thenReturn(List.of(user1, user2, user3, user4));

        notificationService.sendPublicNotificationToAllUsers("text");

        verify(telegramBotClientService).sendText("chatId1", "text");
        verify(telegramBotClientService).sendText("chatId2", "text");
        verify(telegramBotClientService, times(0)).sendText("chatId3", "text");
        verify(telegramBotClientService, times(0)).sendText("chatId4", "text");
    }

    @Test
    public void sendUbiStatsUpdateNotificationToUser_should_send_to_user_if_with_true_ubiStatsUpdateEnabled() {
        ToBeNotifiedUser user1 = new ToBeNotifiedUser("chatId1", null, null, true, null, null);
        ToBeNotifiedUser user2 = new ToBeNotifiedUser("chatId2", null, null, false, null, null);
        ToBeNotifiedUser user3 = new ToBeNotifiedUser("chatId3", null, null, null, null, null);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user1);
        when(userDatabaseService.getToBeNotifiedUser(2L)).thenReturn(user2);
        when(userDatabaseService.getToBeNotifiedUser(3L)).thenReturn(user3);

        notificationService.sendUbiStatsUpdateNotificationToUser(1L, "text");
        notificationService.sendUbiStatsUpdateNotificationToUser(2L, "text");
        notificationService.sendUbiStatsUpdateNotificationToUser(3L, "text");

        verify(telegramBotClientService).sendText("chatId1", "text");
        verify(telegramBotClientService, never()).sendText("chatId2", "text");
        verify(telegramBotClientService, never()).sendText("chatId3", "text");
    }

    @Test
    public void sendTradeManagerNotificationToUser_should_send_to_user_if_with_true_tradeManagerEnabled() {
        ToBeNotifiedUser user1 = new ToBeNotifiedUser("chatId1", null, null, null, true, null);
        ToBeNotifiedUser user2 = new ToBeNotifiedUser("chatId2", null, null, null, false, null);
        ToBeNotifiedUser user3 = new ToBeNotifiedUser("chatId3", null, null, null, null, null);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user1);
        when(userDatabaseService.getToBeNotifiedUser(2L)).thenReturn(user2);
        when(userDatabaseService.getToBeNotifiedUser(3L)).thenReturn(user3);

        notificationService.sendTradeManagerNotificationToUser(1L, "text");
        notificationService.sendTradeManagerNotificationToUser(2L, "text");
        notificationService.sendTradeManagerNotificationToUser(3L, "text");

        verify(telegramBotClientService).sendText("chatId1", "text");
        verify(telegramBotClientService, never()).sendText("chatId2", "text");
        verify(telegramBotClientService, never()).sendText("chatId3", "text");
    }

    @Test
    public void sendAuthorizationNotificationToUser_should_send_to_user_if_with_true_authorizationEnabled() {
        ToBeNotifiedUser user1 = new ToBeNotifiedUser("chatId1", null, null, null, null, true);
        ToBeNotifiedUser user2 = new ToBeNotifiedUser("chatId2", null, null, null, null, false);
        ToBeNotifiedUser user3 = new ToBeNotifiedUser("chatId3", null, null, null, null, null);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user1);
        when(userDatabaseService.getToBeNotifiedUser(2L)).thenReturn(user2);
        when(userDatabaseService.getToBeNotifiedUser(3L)).thenReturn(user3);

        notificationService.sendAuthorizationNotificationToUser(1L, "text");
        notificationService.sendAuthorizationNotificationToUser(2L, "text");
        notificationService.sendAuthorizationNotificationToUser(3L, "text");

        verify(telegramBotClientService).sendText("chatId1", "text");
        verify(telegramBotClientService, never()).sendText("chatId2", "text");
        verify(telegramBotClientService, never()).sendText("chatId3", "text");
    }
}