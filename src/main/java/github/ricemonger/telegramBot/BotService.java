package github.ricemonger.telegramBot;

import github.ricemonger.marketplace.databases.neo4j.services.TelegramUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BotService {

    private final TelegramUserService telegramUserService;

    public void notifyAllUsersAboutItemAmountIncrease(int expectedItemCount, int actualItemCount) {
        String message = "The amount of items on marketplace increased from " + expectedItemCount + " to " + actualItemCount + "\n" +
                "Probably marketplace was updated and new items were added";
        notifyAllUsers(message);
    }

    private void notifyAllUsers(String message) {
        telegramUserService.notifyAllUsers(message);
    }
}
