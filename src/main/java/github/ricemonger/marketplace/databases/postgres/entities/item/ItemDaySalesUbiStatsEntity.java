package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.ItemDaySales;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    private Date date;

    private int lowestPrice;
    private int averagePrice;
    private int highestPrice;
    private int itemsCount;
    private int averageNoEdgesPrice;

    public ItemDaySalesUbiStatsEntity(ItemEntity item, ItemDaySales itemDaySales) {
        this.item = item;
        this.date = itemDaySales.getDate();
        this.lowestPrice = itemDaySales.getLowestPrice();
        this.averagePrice = itemDaySales.getAveragePrice();
        this.highestPrice = itemDaySales.getHighestPrice();
        this.itemsCount = itemDaySales.getItemsCount();
        this.averageNoEdgesPrice = itemDaySales.getAverageNoEdgesPrice();
    }

    public ItemDaySales toItemDaySales() {
        ItemDaySales itemDaySales = new ItemDaySales();
        itemDaySales.setDate(this.date);
        itemDaySales.setLowestPrice(this.lowestPrice);
        itemDaySales.setAveragePrice(this.averagePrice);
        itemDaySales.setHighestPrice(this.highestPrice);
        itemDaySales.setItemsCount(this.itemsCount);
        itemDaySales.setAverageNoEdgesPrice(this.averageNoEdgesPrice);
        return itemDaySales;
    }
}
