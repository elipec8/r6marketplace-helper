package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utils.enums.InputState;
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
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

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof TelegramUserInputEntity entity) {
            return telegramUser.isEqual(entity.telegramUser) &&
                   inputState == entity.inputState;
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof TelegramUserInputEntity entity) {
            return isEqual(entity) &&
                   Objects.equals(value, entity.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return "TelegramUserInputEntity(chatId=" + getChatId_() + ", inputState=" + inputState + ", value=" + value + ")";
    }
}
