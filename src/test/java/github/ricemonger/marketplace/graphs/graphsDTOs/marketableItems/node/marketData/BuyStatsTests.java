package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyStatsTests {

    public final static BuyStats BUY_STATS = new BuyStats("1",2,3,4);

    public final static BuyStats SAME_BUY_STATS = new BuyStats("1",2,3,4);

    public final static BuyStats ALT_ID = new BuyStats("99",2,3,4);

    public final static BuyStats ALT_LOWER_PRICE = new BuyStats("1",99,3,4);

    public final static BuyStats ALT_HIGHEST_PRICE = new BuyStats("1",2,99,4);

    public final static BuyStats ALT_ACTIVE_COUNT = new BuyStats("1",2,3,99);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(BUY_STATS, SAME_BUY_STATS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(BUY_STATS, ALT_ID);
        assertNotEquals(BUY_STATS, ALT_LOWER_PRICE);
        assertNotEquals(BUY_STATS, ALT_HIGHEST_PRICE);
        assertNotEquals(BUY_STATS, ALT_ACTIVE_COUNT);
    }
}