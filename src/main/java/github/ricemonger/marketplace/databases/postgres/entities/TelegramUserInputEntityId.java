package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.executors.InputState;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserInputEntityId {
    private String chatId;
    private InputState inputState;
}
