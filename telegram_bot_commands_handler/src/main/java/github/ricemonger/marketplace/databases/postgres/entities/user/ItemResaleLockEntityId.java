package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.utilspostgresschema.entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {
    private UbiAccountStatsEntity ubiAccount;
    private ItemEntity item;
}
