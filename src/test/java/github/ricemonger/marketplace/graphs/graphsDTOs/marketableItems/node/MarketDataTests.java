package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node;

import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.SellStats;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarketDataTests {

    public final static SellStats SELL_STATS = new SellStats("1",2,3,4);

    public final static SellStats DIFF_SELL_STATS = new SellStats("99",2,3,4);

    public final static BuyStats BUY_STATS = new BuyStats("1",2,3,4);

    public final static BuyStats DIFF_BUY_STATS = new BuyStats("99",2,3,4);

    public final static LastSoldAt LAST_SOLD_AT = new LastSoldAt("1",2,"3");

    public final static LastSoldAt DIFF_LAST_SOLD_AT = new LastSoldAt("99",2,"3");



    public static final MarketData MARKET_DATA = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData SAME_MARKET_DATA = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData ALT_SELL_STATS = new MarketData(
            new SellStats[]{DIFF_SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData ALT_BUY_STATS = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{DIFF_BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData ALT_LAST_SOLD_AT = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{DIFF_LAST_SOLD_AT});

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(MARKET_DATA,SAME_MARKET_DATA);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(MARKET_DATA,ALT_SELL_STATS);
        assertNotEquals(MARKET_DATA,ALT_BUY_STATS);
        assertNotEquals(MARKET_DATA,ALT_LAST_SOLD_AT);
    }
}