package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Table(name = "telegram_user")
@Entity(name = "telegram_user_user_id_entity")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUserUserIdEntity {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserIdEntity user;

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelegramUserUserIdEntity telegramUserEntity)) {
            return false;
        }
        return Objects.equals(chatId, telegramUserEntity.chatId);
    }
}
