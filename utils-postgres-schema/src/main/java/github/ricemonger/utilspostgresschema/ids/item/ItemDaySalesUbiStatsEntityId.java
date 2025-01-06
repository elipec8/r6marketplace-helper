package github.ricemonger.utilspostgresschema.ids.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntityId {
    private ItemEntity item;
    private LocalDate date;
}
