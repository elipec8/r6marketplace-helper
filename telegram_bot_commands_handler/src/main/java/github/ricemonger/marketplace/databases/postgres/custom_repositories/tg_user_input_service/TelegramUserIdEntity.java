package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
public class TelegramUserIdEntity {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelegramUserIdEntity telegramUserEntity)) {
            return false;
        }
        return Objects.equals(chatId, telegramUserEntity.chatId);
    }
}
