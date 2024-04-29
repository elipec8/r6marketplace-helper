package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems;

import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.SellStats;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NodeTests {

    public final static SellStats SELL_STATS = new SellStats("1",2,3,4);

    public final static SellStats DIFF_SELL_STATS = new SellStats("99",2,3,4);

    public final static BuyStats BUY_STATS = new BuyStats("1",2,3,4);

    public final static LastSoldAt LAST_SOLD_AT = new LastSoldAt("1",2,"3");


    public static final MarketData MARKET_DATA = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData DIFF_MARKET_DATA = new MarketData(
            new SellStats[]{DIFF_SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public final static Item ITEM = new Item("1","2","3", List.of("4"),"5");

    public final static Item DIFF_ITEM = new Item("99","2","3", List.of("4"),"5");

    public final static Node NODE = new Node(ITEM, MARKET_DATA);

    public final static Node SAME_NODE = new Node(ITEM, MARKET_DATA);

    public final static Node ALT_ITEM = new Node(DIFF_ITEM, MARKET_DATA);

    public final static Node ALT_MARKET_DATA = new Node(ITEM, DIFF_MARKET_DATA);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(NODE,SAME_NODE);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(NODE,ALT_ITEM);
        assertNotEquals(NODE,ALT_MARKET_DATA);
    }
}