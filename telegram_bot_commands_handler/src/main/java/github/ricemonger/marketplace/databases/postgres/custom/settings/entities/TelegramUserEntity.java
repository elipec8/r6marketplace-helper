package github.ricemonger.marketplace.databases.postgres.custom.settings.entities;

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
public class TelegramUserEntity {
    @Id
    @Column(name = "chat_id")
    private String chatId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "item_show_messages_limit")
    private Integer itemShowMessagesLimit = 50;
    @Column(name = "item_show_few_in_message_flag")
    private Boolean itemShowFewInMessageFlag = false;

    @Override
    public int hashCode() {
        return Objects.hash(chatId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelegramUserEntity telegramUserEntity)) {
            return false;
        }
        return Objects.equals(chatId, telegramUserEntity.chatId);
    }
}
