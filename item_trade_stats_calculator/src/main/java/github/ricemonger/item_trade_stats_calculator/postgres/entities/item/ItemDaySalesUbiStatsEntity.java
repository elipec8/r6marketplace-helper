package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

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
    private ItemIdEntity item;

    @Id
    private LocalDate date;

    private Integer lowestPrice;
    private Integer averagePrice;
    private Integer highestPrice;
    private Integer itemsCount;

    public String getItemId_() {
        return item.getItemId();
    }

    @Override
    public String toString() {
        return "ItemDaySalesUbiStatsEntity(itemId=" + getItemId_() + ", date=" + date + ", lowestPrice=" + lowestPrice + ", averagePrice=" + averagePrice +
               ", highestPrice=" + highestPrice + ", itemsCount=" + itemsCount + ")";
    }
}
