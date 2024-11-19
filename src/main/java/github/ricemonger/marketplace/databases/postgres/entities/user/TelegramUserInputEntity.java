package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.TelegramUserInput;
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
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "chatId", referencedColumnName = "chatId")
    private TelegramUserEntity telegramUser;

    @Id
    @Enumerated(EnumType.ORDINAL)
    private InputState inputState;

    @Column(name = "input_value") // "value" column name conflicts with H2
    private String value;

    public TelegramUserInputEntity(TelegramUserEntity user, InputState inputState) {
        this.telegramUser = user;
        this.inputState = inputState;
    }

    public TelegramUserInput toTelegramUserInput() {
        TelegramUserInput telegramUserInput = new TelegramUserInput();
        if (this.telegramUser != null) {
            telegramUserInput.setChatId(this.telegramUser.getChatId());
        } else {
            log.error("telegramUser is null.");
            telegramUserInput.setChatId(null);
        }
        telegramUserInput.setInputState(this.inputState);
        telegramUserInput.setValue(this.value);
        return telegramUserInput;
    }
}
