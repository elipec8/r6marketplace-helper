package github.ricemonger.trades_manager.services;

import org.springframework.stereotype.Service;

@Service
public class TelegramBotService {
    public void sendPrivateNotification(Long userId, String string) {
        System.out.println("Sending private notification to user " + userId + ": " + string);
    }
}
