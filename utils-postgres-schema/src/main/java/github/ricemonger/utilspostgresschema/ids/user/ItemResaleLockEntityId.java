package github.ricemonger.utilspostgresschema.ids.user;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdUbiAccountStatsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemResaleLockEntityId {
    private IdUbiAccountStatsEntity ubiAccount;
    private IdItemEntity item;
}
