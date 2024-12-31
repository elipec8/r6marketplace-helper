package github.ricemonger.utilspostgresschema.id_entities.user;

import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "telegram_user_input")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TelegramUserInputEntityId.class)
public class IdTelegramUserInputEntity {
    @MapsId
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_id", referencedColumnName = "chat_id")
    private IdTelegramUserEntity telegramUser;

    @Id
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_state")
    private InputState inputState;

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
        if (!(o instanceof IdTelegramUserInputEntity telegramUserInputEntity)) {
            return false;
        }
        return Objects.equals(telegramUser, telegramUserInputEntity.telegramUser) &&
               Objects.equals(inputState, telegramUserInputEntity.inputState);
    }
}
