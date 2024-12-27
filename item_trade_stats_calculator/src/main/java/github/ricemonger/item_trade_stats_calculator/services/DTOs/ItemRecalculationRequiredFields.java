package github.ricemonger.item_trade_stats_calculator.services.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemIdFieldI;
import github.ricemonger.utils.enums.ItemRarity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRecalculationRequiredFields implements ItemIdFieldI {
    private String itemId;

    private ItemRarity rarity;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    public Item toItem() {
        Item item = new Item();
        item.setItemId(this.itemId);
        item.setRarity(this.rarity);
        item.setMaxBuyPrice(this.maxBuyPrice);
        item.setBuyOrdersCount(this.buyOrdersCount);
        item.setMinSellPrice(this.minSellPrice);
        item.setSellOrdersCount(this.sellOrdersCount);
        return item;
    }
}
