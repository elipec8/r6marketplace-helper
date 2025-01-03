package github.ricemonger.users_ubi_accs_reauthorizer.services;

import org.springframework.stereotype.Service;

@Service
public class TelegramBotClientService {

    public void sendNotificationToUser(Long id, String text) {
        System.out.println("Sending notification to user with id: " + id + " and message: " + text);
    }
}
