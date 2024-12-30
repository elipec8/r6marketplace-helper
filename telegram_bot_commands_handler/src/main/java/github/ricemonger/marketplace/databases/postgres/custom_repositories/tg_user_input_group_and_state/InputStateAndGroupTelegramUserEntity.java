package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_group_and_state;

import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "telegram_user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InputStateAndGroupTelegramUserEntity {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_state")
    private InputState inputState = InputState.BASE;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "input_group")
    private InputGroup inputGroup = InputGroup.BASE;

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InputStateAndGroupTelegramUserEntity telegramUserEntity)) {
            return false;
        }
        return Objects.equals(chatId, telegramUserEntity.chatId);
    }
}
