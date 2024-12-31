package github.ricemonger.utilspostgresschema.ids.user;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManagerEntityId {
    private IdUserEntity user;
    private IdItemEntity item;
}
