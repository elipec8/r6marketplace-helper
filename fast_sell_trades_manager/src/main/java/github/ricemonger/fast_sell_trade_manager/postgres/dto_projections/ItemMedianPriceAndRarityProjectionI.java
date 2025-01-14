package github.ricemonger.fast_sell_trade_manager.postgres.dto_projections;

import github.ricemonger.utils.enums.ItemRarity;

public interface ItemMedianPriceAndRarityProjectionI {
    String getItemId();

    void setItemId(String itemId);

    ItemRarity getRarity();

    void setRarity(ItemRarity rarity);

    Integer getMonthMedianPrice();

    void setMonthMedianPrice(Integer monthMedianPrice);
}
