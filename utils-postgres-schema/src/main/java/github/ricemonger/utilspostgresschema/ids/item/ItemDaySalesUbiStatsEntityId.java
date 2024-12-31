package github.ricemonger.utilspostgresschema.ids.item;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntityId {
    private IdItemEntity item;
    private LocalDate date;
}
