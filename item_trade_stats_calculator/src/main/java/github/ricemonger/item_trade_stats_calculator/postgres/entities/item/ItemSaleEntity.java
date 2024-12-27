package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "item_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ItemSaleEntityId.class)
public class ItemSaleEntity {
    @MapsId
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId", referencedColumnName = "itemId")
    private ItemIdEntity item;
    @Id
    private LocalDateTime soldAt;
    private Integer price;

    public String getItemId_() {
        return item.getItemId();
    }

    @Override
    public String toString() {
        return "ItemSaleEntity(itemId=" + getItemId_() + ", soldAt=" + soldAt + ", price=" + price + ")";
    }
}
