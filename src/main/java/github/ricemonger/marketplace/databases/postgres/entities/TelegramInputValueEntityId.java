package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.executors.InputState;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramInputValueEntityId {

    private String chatId;

    private InputState inputState;
}
