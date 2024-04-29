package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyStatsEntityTests {

    public final static BuyStatsEntity BUY_STATS = new BuyStatsEntity("1", 2, 3, 4);

    public final static BuyStatsEntity SAME_BUY_STATS = new BuyStatsEntity("1", 2, 3, 4);

    public final static BuyStatsEntity ALT_ID = new BuyStatsEntity("99", 2, 3, 4);

    public final static BuyStatsEntity ALT_LOWEST_PRICE = new BuyStatsEntity("1", 99, 3, 4);

    public final static BuyStatsEntity ALT_HIGHEST_PRICE = new BuyStatsEntity("1", 2, 99, 4);

    public final static BuyStatsEntity ALT_ACTIVE_COUNT = new BuyStatsEntity("1", 2, 3, 99);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(BUY_STATS, SAME_BUY_STATS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(BUY_STATS, ALT_ID);
        assertNotEquals(BUY_STATS, ALT_LOWEST_PRICE);
        assertNotEquals(BUY_STATS, ALT_HIGHEST_PRICE);
        assertNotEquals(BUY_STATS, ALT_ACTIVE_COUNT);
    }
}