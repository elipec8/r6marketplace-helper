package github.ricemonger.fast_sell_trade_manager.postgres.services.entity_mappers.item;

import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjection;
import github.ricemonger.fast_sell_trade_manager.postgres.dto_projections.ItemMedianPriceAndRarityProjectionI;
import github.ricemonger.fast_sell_trade_manager.services.DTOs.ItemMedianPriceAndRarity;
import github.ricemonger.utils.enums.ItemRarity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemEntityMapperTest {
    @Autowired
    private ItemEntityMapper itemEntityMapper;

    @Test
    public void createItemMedianPriceAndRarity_should_returnItemMedianPriceAndRarity() {
        ItemMedianPriceAndRarityProjectionI projection = new ItemMedianPriceAndRarityProjection("itemId",
                ItemRarity.RARE,
                100);

        ItemMedianPriceAndRarity itemMedianPriceAndRarity = itemEntityMapper.createItemMedianPriceAndRarity(projection);

        assertEquals("itemId", itemMedianPriceAndRarity.getItemId());
        assertEquals(ItemRarity.RARE, itemMedianPriceAndRarity.getRarity());
        assertEquals(100, itemMedianPriceAndRarity.getMonthMedianPrice());
    }

}