package github.ricemonger.marketplace.databases.postgres.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelegramLinkedTradeManagerByItemIdEntityId {
    private String chatId;
    private String itemId;
}
