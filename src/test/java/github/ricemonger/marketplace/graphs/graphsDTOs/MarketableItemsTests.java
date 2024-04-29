package github.ricemonger.marketplace.graphs.graphsDTOs;

import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData.SellStats;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarketableItemsTests {

    public final static SellStats SELL_STATS = new SellStats("1",2,3,4);

    public final static BuyStats BUY_STATS = new BuyStats("1",2,3,4);

    public final static LastSoldAt LAST_SOLD_AT = new LastSoldAt("1",2,"3");


    public static final MarketData MARKET_DATA = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public final static Item ITEM = new Item("1","2","3", List.of("4"),"5");

    public final static Item DIFF_ITEM = new Item("99","2","3", List.of("4"),"5");

    public final static Node NODE = new Node(ITEM, MARKET_DATA);

    public final static Node DIFF_NODE = new Node(DIFF_ITEM, MARKET_DATA);

    public final static MarketableItems MARKETABLE_ITEMS = new MarketableItems(List.of(NODE), 1);

    public final static MarketableItems SAME_MARKETABLE_ITEMS = new MarketableItems(List.of(NODE), 1);

    public final static MarketableItems ALT_NODES = new MarketableItems(List.of(DIFF_NODE), 1);

    public final static MarketableItems ALT_TOTAL_COUNT = new MarketableItems(List.of(NODE), 99);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(MARKETABLE_ITEMS,SAME_MARKETABLE_ITEMS);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(MARKETABLE_ITEMS,ALT_NODES);
        assertNotEquals(MARKETABLE_ITEMS,ALT_TOTAL_COUNT);
    }
}