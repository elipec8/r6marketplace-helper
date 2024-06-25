package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity(name = "telegram_user_input")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramUserInputEntityId.class)
public class TelegramUserInputEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private TelegramUserEntity telegramUser;

    @Id
    private InputState inputState;

    private String value;

    public TelegramUserInput toTelegramUserInput() {
        TelegramUserInput telegramUserInput = new TelegramUserInput();
        if(this.telegramUser != null) {
            telegramUserInput.setChatId(this.telegramUser.getChatId());
        }
        else{
            log.error("TelegramUserInputEntity.toTelegramUserInput: telegramUser is null.");
            telegramUserInput.setChatId(null);
        }
        telegramUserInput.setInputState(this.inputState);
        telegramUserInput.setValue(this.value);
        return telegramUserInput;
    }
}
