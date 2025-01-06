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
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", true, false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService).sendText("chatId", "text");
    }

    @Test
    public void sendPrivateNotificationToUser_should_not_send_if_user_has_false_private_flag_true_public() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", false, true);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPrivateNotificationToUser_should_not_send_if_user_has_null_private_flag_true_public() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", null, true);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPrivateNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_send_if_user_has_true_public_Notification_flag_false_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", false, true);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_not_send_if_user_has_false_public_Notification_flag_true_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", true, false);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicToUser_should_not_send_if_user_has_null_public_Notification_flag_true_private() {
        ToBeNotifiedUser user = new ToBeNotifiedUser("chatId", true, null);

        when(userDatabaseService.getToBeNotifiedUser(1L)).thenReturn(user);

        notificationService.sendPublicNotificationToUser(1L, "text");

        verify(telegramBotClientService, times(0)).sendText("chatId", "text");
    }

    @Test
    public void sendPublicNotificationToAllUsers_should_send_to_expected_users() {
        ToBeNotifiedUser user1 = new ToBeNotifiedUser("chatId1", false, true);
        ToBeNotifiedUser user2 = new ToBeNotifiedUser("chatId2", null, true);
        ToBeNotifiedUser user3 = new ToBeNotifiedUser("chatId3", true, false);
        ToBeNotifiedUser user4 = new ToBeNotifiedUser("chatId4", true, null);

        when(userDatabaseService.getAllToBeNotifiedUsers()).thenReturn(List.of(user1, user2, user3, user4));

        notificationService.sendPublicNotificationToAllUsers("text");

        verify(telegramBotClientService).sendText("chatId1", "text");
        verify(telegramBotClientService).sendText("chatId2", "text");
        verify(telegramBotClientService, times(0)).sendText("chatId3", "text");
        verify(telegramBotClientService, times(0)).sendText("chatId4", "text");
    }
}