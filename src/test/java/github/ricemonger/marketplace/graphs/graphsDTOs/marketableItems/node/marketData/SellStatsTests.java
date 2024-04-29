package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SellStatsTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(SELL_STATS, SELL_STATS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(SELL_STATS, SELL_STATS_ALT_ID);
        assertNotEquals(SELL_STATS, SELL_STATS_ALT_LOWER_PRICE);
        assertNotEquals(SELL_STATS, SELL_STATS_ALT_HIGHEST_PRICE);
        assertNotEquals(SELL_STATS, SELL_STATS_ALT_ACTIVE_COUNT);
    }

}