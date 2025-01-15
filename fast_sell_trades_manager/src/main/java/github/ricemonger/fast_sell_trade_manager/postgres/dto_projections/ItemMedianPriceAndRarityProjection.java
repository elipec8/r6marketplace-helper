package github.ricemonger.fast_sell_trade_manager.postgres.dto_projections;

import github.ricemonger.utils.enums.ItemRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMedianPriceAndRarityProjection implements ItemMedianPriceAndRarityProjectionI {
    private String itemId;
    private ItemRarity rarity;
    private Integer monthMedianPrice;

    public ItemMedianPriceAndRarityProjection(ItemMedianPriceAndRarityProjectionI projectionI) {
        this.itemId = projectionI.getItemId();
        this.rarity = projectionI.getRarity();
        this.monthMedianPrice = projectionI.getMonthMedianPrice();
    }
}
