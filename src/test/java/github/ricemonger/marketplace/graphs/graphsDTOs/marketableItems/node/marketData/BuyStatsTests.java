package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.*;

class BuyStatsTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(BUY_STATS, BUY_STATS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(BUY_STATS, BUY_STATS_ALT_ID);
        assertNotEquals(BUY_STATS, BUY_STATS_ALT_LOWER_PRICE);
        assertNotEquals(BUY_STATS, BUY_STATS_ALT_HIGHEST_PRICE);
        assertNotEquals(BUY_STATS, BUY_STATS_ALT_ACTIVE_COUNT);
    }
}