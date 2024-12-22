package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.telegramBot.InputState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

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

    public String getChatId_() {
        return telegramUser.getChatId();
    }

    public boolean isFullyEqualExceptTelegramUser(Object o) {
        if (this == o) return true;
        if (o instanceof TelegramUserInputEntity entity) {
            return Objects.equals(getChatId_(), entity.getChatId_()) &&
                   inputState == entity.inputState &&
                   Objects.equals(value, entity.value);
        }
        return false;
    }
}
