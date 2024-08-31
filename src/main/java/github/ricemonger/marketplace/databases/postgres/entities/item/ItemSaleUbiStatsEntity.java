package github.ricemonger.marketplace.databases.postgres.entities.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity(name = "item_sale_ubi_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemSaleUbiStatsEntity {
    //@Id
    private ItemEntity item;
}
