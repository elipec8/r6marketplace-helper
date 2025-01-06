package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private ManageableUserEntity user;
    private ItemIdEntity item;
}
