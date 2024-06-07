package github.ricemonger.marketplace.databases.postgres.entities;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramUbiUserEntityId {
    private String chatId;
    private String email;
}
