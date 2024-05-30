package github.ricemonger.telegramBot;

import github.ricemonger.marketplace.services.TelegramUserService;
import github.ricemonger.telegramBot.client.TelegramBotClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BotService {

    private final TelegramBotClientService telegramBotClientService;

    private final TelegramUserService telegramUserService;

    public void notifyAllUsersAboutItemAmountIncrease(int expectedItemCount, int actualItemCount) {
        String message = "The amount of items on marketplace increased from " + expectedItemCount + " to " + actualItemCount + "\n" +
                "Probably marketplace was updated and new items were added";
        notifyAllUsers(message);
    }

    private void notifyAllUsers(String message) {
        List<String> chatIds = telegramUserService.getAllChatIdsForNotifiableUsers();

        chatIds.forEach(chatId -> telegramBotClientService.sendText(chatId, message));
    }
}
