package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SellStatsTests {

    public final static SellStats SELL_STATS = new SellStats("1",2,3,4);

    public final static SellStats SAME_SELL_STATS = new SellStats("1",2,3,4);

    public final static SellStats ALT_ID = new SellStats("99",2,3,4);

    public final static SellStats ALT_LOWER_PRICE = new SellStats("1",99,3,4);

    public final static SellStats ALT_HIGHEST_PRICE = new SellStats("1",2,99,4);

    public final static SellStats ALT_ACTIVE_COUNT = new SellStats("1",2,3,99);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(SELL_STATS, SAME_SELL_STATS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(SELL_STATS, ALT_ID);
        assertNotEquals(SELL_STATS, ALT_LOWER_PRICE);
        assertNotEquals(SELL_STATS, ALT_HIGHEST_PRICE);
        assertNotEquals(SELL_STATS, ALT_ACTIVE_COUNT);
    }

}