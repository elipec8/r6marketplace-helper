package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SellStatsEntityTests {

    public final static SellStatsEntity SELL_STATS = new SellStatsEntity("1", 2, 3, 4);

    public final static SellStatsEntity SAME_SELL_STATS = new SellStatsEntity("1", 2, 3, 4);

    public final static SellStatsEntity ALT_ID = new SellStatsEntity("99", 2, 3, 4);

    public final static SellStatsEntity ALT_LOWEST_PRICE = new SellStatsEntity("1", 99, 3, 4);

    public final static SellStatsEntity ALT_HIGHEST_PRICE = new SellStatsEntity("1", 2, 99, 4);

    public final static SellStatsEntity ALT_ACTIVE_COUNT = new SellStatsEntity("1", 2, 3, 99);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(SELL_STATS, SAME_SELL_STATS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(SELL_STATS, ALT_ID);
        assertNotEquals(SELL_STATS, ALT_LOWEST_PRICE);
        assertNotEquals(SELL_STATS, ALT_HIGHEST_PRICE);
        assertNotEquals(SELL_STATS, ALT_ACTIVE_COUNT);
    }

}