package github.ricemonger.utilspostgresschema.full_entities.item;

import github.ricemonger.utilspostgresschema.id_entities.item.IdItemDaySalesUbiStatsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDaySalesUbiStatsEntity extends IdItemDaySalesUbiStatsEntity {
    @Column(name = "lowest_price")
    private Integer lowestPrice;
    @Column(name = "average_price")
    private Integer averagePrice;
    @Column(name = "highest_price")
    private Integer highestPrice;
    @Column(name = "items_count")
    private Integer itemsCount;
}
