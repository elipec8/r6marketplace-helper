package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service;

import github.ricemonger.utils.enums.InputState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "telegram_user_input")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramUserIdInputEntityId.class)
public class TelegramUserIdInputEntity {
    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private TelegramUserIdEntity telegramUser;

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_state")
    private InputState inputState;

    @Column(name = "input_value")
    private String value;

    public String getChatId_() {
        return telegramUser.getChatId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(telegramUser, inputState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelegramUserIdInputEntity telegramUserInputEntity)) {
            return false;
        }
        return Objects.equals(telegramUser, telegramUserInputEntity.telegramUser) &&
               Objects.equals(inputState, telegramUserInputEntity.inputState);
    }
}
