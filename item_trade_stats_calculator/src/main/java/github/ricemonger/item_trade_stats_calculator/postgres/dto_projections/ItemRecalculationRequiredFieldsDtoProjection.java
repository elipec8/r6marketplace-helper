package github.ricemonger.item_trade_stats_calculator.postgres.dto_projections;

import github.ricemonger.utils.enums.ItemRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRecalculationRequiredFieldsDtoProjection {
    private String itemId;

    private ItemRarity rarity;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;
}
