package github.ricemonger.marketplace.databases.postgres.entities.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "item_day_sales_ubi_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemDaySalesUbiStatsEntityId.class)
public class ItemDaySalesUbiStatsEntity {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemEntity item;

    @Id
    private LocalDate date;

    private int lowestPrice;
    private int averagePrice;
    private int highestPrice;
    private int itemsCount;

    public String getItemId() {
        return item.getItemId();
    }
}
