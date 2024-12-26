package github.ricemonger.marketplace.databases.postgres.entities.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

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

    private Integer lowestPrice;
    private Integer averagePrice;
    private Integer highestPrice;
    private Integer itemsCount;

    public String getItemId_() {
        return item.getItemId();
    }

    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemDaySalesUbiStatsEntity entity) {
            return item.isEqual(entity.item) &&
                   Objects.equals(date, entity.date);
        }
        return false;
    }

    public boolean isFullyEqual(Object o) {
        if (this == o) return true;
        if (o instanceof ItemDaySalesUbiStatsEntity entity) {
            return isEqual(entity) &&
                   Objects.equals(lowestPrice, entity.lowestPrice) &&
                   Objects.equals(averagePrice, entity.averagePrice) &&
                   Objects.equals(highestPrice, entity.highestPrice) &&
                   Objects.equals(itemsCount, entity.itemsCount);
        }
        return false;
    }

    @Override
    public String toString() {
        return "ItemDaySalesUbiStatsEntity(itemId=" + getItemId_() + ", date=" + date + ", lowestPrice=" + lowestPrice + ", averagePrice=" + averagePrice +
               ", highestPrice=" + highestPrice + ", itemsCount=" + itemsCount + ")";
    }
}
