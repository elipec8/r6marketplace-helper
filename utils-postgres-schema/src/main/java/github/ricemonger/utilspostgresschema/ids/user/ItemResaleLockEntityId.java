package github.ricemonger.utilspostgresschema.ids.user;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
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
