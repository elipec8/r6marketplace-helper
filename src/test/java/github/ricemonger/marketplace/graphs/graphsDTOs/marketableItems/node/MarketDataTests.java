package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarketDataTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(MARKET_DATA, MARKET_DATA_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(MARKET_DATA,MARKET_DATA_ALT_SELL_STATS);
        assertNotEquals(MARKET_DATA,MARKET_DATA_ALT_BUY_STATS);
        assertNotEquals(MARKET_DATA,MARKET_DATA_ALT_LAST_SOLD_AT);
    }
}