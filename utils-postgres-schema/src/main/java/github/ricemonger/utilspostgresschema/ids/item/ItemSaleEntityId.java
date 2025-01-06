package github.ricemonger.utilspostgresschema.ids.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleEntityId implements Serializable {
    private ItemEntity item;
    private LocalDateTime soldAt;
}
