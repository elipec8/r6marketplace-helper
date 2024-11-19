package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
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

    public ItemDaySalesUbiStatsEntity(ItemEntity item, ItemDaySalesUbiStats itemDaySalesUbiStats) {
        this.item = item;
        this.date = itemDaySalesUbiStats.getDate();
        this.lowestPrice = itemDaySalesUbiStats.getLowestPrice();
        this.averagePrice = itemDaySalesUbiStats.getAveragePrice();
        this.highestPrice = itemDaySalesUbiStats.getHighestPrice();
        this.itemsCount = itemDaySalesUbiStats.getItemsCount();
    }

    public ItemDaySalesUbiStats toItemDaySalesUbiStats() {
        ItemDaySalesUbiStats itemDaySalesUbiStats = new ItemDaySalesUbiStats();
        itemDaySalesUbiStats.setItemId(this.item.getItemId());
        itemDaySalesUbiStats.setDate(this.date);
        itemDaySalesUbiStats.setLowestPrice(this.lowestPrice);
        itemDaySalesUbiStats.setAveragePrice(this.averagePrice);
        itemDaySalesUbiStats.setHighestPrice(this.highestPrice);
        itemDaySalesUbiStats.setItemsCount(this.itemsCount);
        return itemDaySalesUbiStats;
    }
}
