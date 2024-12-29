package github.ricemonger.utilspostgresschema.entities.user;

import github.ricemonger.utilspostgresschema.entities.item.ItemEntity;
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
