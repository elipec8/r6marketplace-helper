package github.ricemonger.marketplace.databases.postgres.entities;


import github.ricemonger.telegramBot.executors.InputState;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity(name = "telegram_user_input_values")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramInputValueEntityId.class)
public class TelegramInputValueEntity {

    @Id
    private String chatId;

    @Id
    private InputState inputState;

    private String value;
}
