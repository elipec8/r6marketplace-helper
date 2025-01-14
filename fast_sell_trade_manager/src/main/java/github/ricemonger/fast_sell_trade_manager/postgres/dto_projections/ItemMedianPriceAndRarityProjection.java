package github.ricemonger.fast_sell_trade_manager.postgres.dto_projections;

import github.ricemonger.utils.enums.ItemRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMedianPriceAndRarityProjection {
    private String itemId;
    private ItemRarity rarity;
    private Integer monthMedianPrice;
}
