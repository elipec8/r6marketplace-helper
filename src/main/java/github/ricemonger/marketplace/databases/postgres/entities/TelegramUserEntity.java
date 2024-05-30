package github.ricemonger.marketplace.databases.postgres.entities;

import github.ricemonger.telegramBot.executors.InputGroup;
import github.ricemonger.telegramBot.executors.InputState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "telegram_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserEntity {
    @Id
    private String chatId;

    private InputState inputState = InputState.BASE;
    private InputGroup inputGroup = InputGroup.BASE;

    private boolean publicNotificationsEnabledFlag = true;

    public TelegramUserEntity(String chatId) {
        this.chatId = chatId;
    }
}
