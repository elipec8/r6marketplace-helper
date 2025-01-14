package github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.item;

import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjectionI;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import org.springframework.stereotype.Service;

@Service
public class ItemEntityMapper {

    public ItemMedianPriceAndRarity createItemMedianPriceAndRarity(ItemMedianPriceAndRarityProjectionI projection) {
        return new ItemMedianPriceAndRarity(
                projection.getItemId(),
                projection.getRarity(),
                projection.getMonthMedianPrice()
        );
    }
}
