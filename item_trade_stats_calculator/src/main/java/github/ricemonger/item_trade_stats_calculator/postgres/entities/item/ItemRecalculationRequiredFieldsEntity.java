package github.ricemonger.item_trade_stats_calculator.postgres.entities.item;

import github.ricemonger.utils.enums.ItemRarity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@Table(name = "item")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemRecalculationRequiredFieldsEntity {
    @Id
    private String itemId;

    private ItemRarity rarity;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;
}
