package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private UserEntity user;
    private ItemEntity item;
}
