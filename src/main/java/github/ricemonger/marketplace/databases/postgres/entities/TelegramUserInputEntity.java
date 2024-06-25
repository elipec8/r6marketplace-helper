package github.ricemonger.marketplace.databases.postgres.entities;


import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "telegram_user_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramUserInputEntityId.class)
public class TelegramUserInputEntity {
    @Id
    private String chatId;
    @Id
    private InputState inputState;
    private String value;

    public TelegramUserInputEntity(TelegramUserInput telegramUserInput) {
        this.chatId = telegramUserInput.getChatId();
        this.inputState = telegramUserInput.getInputState();
        this.value = telegramUserInput.getValue();
    }

    public TelegramUserInput toTelegramUserInput() {
        TelegramUserInput telegramUserInput = new TelegramUserInput();
        telegramUserInput.setChatId(this.chatId);
        telegramUserInput.setInputState(this.inputState);
        telegramUserInput.setValue(this.value);
        return telegramUserInput;
    }
}
