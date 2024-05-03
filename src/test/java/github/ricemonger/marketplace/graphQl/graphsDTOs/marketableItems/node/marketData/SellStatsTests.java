package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData;

import github.ricemonger.marketplace.graphQl.graphsDTOs.DtoStatics;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SellStatsTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        Assertions.assertEquals(DtoStatics.SELL_STATS, DtoStatics.SELL_STATS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        Assertions.assertNotEquals(DtoStatics.SELL_STATS, DtoStatics.SELL_STATS_ALT_ID);
        Assertions.assertNotEquals(DtoStatics.SELL_STATS, DtoStatics.SELL_STATS_ALT_LOWER_PRICE);
        Assertions.assertNotEquals(DtoStatics.SELL_STATS, DtoStatics.SELL_STATS_ALT_HIGHEST_PRICE);
        Assertions.assertNotEquals(DtoStatics.SELL_STATS, DtoStatics.SELL_STATS_ALT_ACTIVE_COUNT);
    }

}