package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import github.ricemonger.utils.enums.ItemRarity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Table(name = "item")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemRecalculationRequiredFieldsEntity {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "rarity")
    private ItemRarity rarity;

    @Column(name = "max_buy_price")
    private Integer maxBuyPrice;
    @Column(name = "buy_orders_count")
    private Integer buyOrdersCount;

    @Column(name = "min_sell_price")
    private Integer minSellPrice;
    @Column(name = "sell_orders_count")
    private Integer sellOrdersCount;

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemRecalculationRequiredFieldsEntity itemEntity)) {
            return false;
        }
        return Objects.equals(this.itemId, itemEntity.itemId);
    }
}
