package github.ricemonger.ubi_users_stats_fetcher.services;

import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {
    public void sendPrivateNotification(Long userId, String string) {
        System.out.println("Sending private notification to user " + userId + ": " + string);
    }
}
