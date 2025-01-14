package github.ricemonger.fast_sell_trade_manager.services.DTOs;

import github.ricemonger.utils.enums.ItemRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemMedianPriceAndRarity {
    private String itemId;
    private ItemRarity rarity;
    private Integer monthMedianPrice;
}
