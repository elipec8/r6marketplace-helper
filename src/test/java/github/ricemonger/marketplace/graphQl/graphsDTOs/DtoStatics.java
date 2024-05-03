package github.ricemonger.marketplace.graphQl.graphsDTOs;

import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.Node;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.Item;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.MarketData;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.BuyStats;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.LastSoldAt;
import github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems.node.marketData.SellStats;

import java.util.List;

public class DtoStatics {

    public final static Item ITEM = new Item("1","2","3", List.of("4"),"5");

    public final static Item ITEM_SAME = new Item("1","2","3", List.of("4"),"5");

    public final static Item ITEM_ALT_ID = new Item("99","2","3", List.of("4"),"5");

    public final static Item ITEM_ALT_ASSERT_URL = new Item("1","99","3", List.of("4"),"5");

    public final static Item ITEM_ALT_NAME = new Item("1","2","99", List.of("4"),"5");

    public final static Item ITEM_ALT_TAGS = new Item("1","2","3", List.of("99"),"5");

    public final static Item ITEM_ALT_TYPE = new Item("1","2","3", List.of("4"),"99");



    public final static SellStats SELL_STATS = new SellStats("1",2,3,4);

    public final static SellStats SELL_STATS_SAME = new SellStats("1",2,3,4);

    public final static SellStats SELL_STATS_ALT_ID = new SellStats("99",2,3,4);

    public final static SellStats SELL_STATS_ALT_LOWER_PRICE = new SellStats("1",99,3,4);

    public final static SellStats SELL_STATS_ALT_HIGHEST_PRICE = new SellStats("1",2,99,4);

    public final static SellStats SELL_STATS_ALT_ACTIVE_COUNT = new SellStats("1",2,3,99);



    public final static BuyStats BUY_STATS = new BuyStats("1",2,3,4);

    public final static BuyStats BUY_STATS_SAME = new BuyStats("1",2,3,4);

    public final static BuyStats BUY_STATS_ALT_ID = new BuyStats("99",2,3,4);

    public final static BuyStats BUY_STATS_ALT_LOWER_PRICE = new BuyStats("1",99,3,4);

    public final static BuyStats BUY_STATS_ALT_HIGHEST_PRICE = new BuyStats("1",2,99,4);

    public final static BuyStats BUY_STATS_ALT_ACTIVE_COUNT = new BuyStats("1",2,3,99);



    public final static LastSoldAt LAST_SOLD_AT = new LastSoldAt("1",2,"3");

    public final static LastSoldAt LAST_SOLD_AT_SAME = new LastSoldAt("1",2,"3");

    public final static LastSoldAt LAST_SOLD_AT_ALT_ID = new LastSoldAt("99",2,"3");

    public final static LastSoldAt LAST_SOLD_AT_ALT_PRICE = new LastSoldAt("1",99,"3");

    public final static LastSoldAt LAST_SOLD_AT_ALT_PREFORMED_AT = new LastSoldAt("1",2,"99");



    public static final MarketData MARKET_DATA = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData MARKET_DATA_SAME = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData MARKET_DATA_ALT_SELL_STATS = new MarketData(
            new SellStats[]{SELL_STATS_ALT_ID},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData MARKET_DATA_ALT_BUY_STATS = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS_ALT_ID},
            new LastSoldAt[]{LAST_SOLD_AT});

    public static final MarketData MARKET_DATA_ALT_LAST_SOLD_AT = new MarketData(
            new SellStats[]{SELL_STATS},
            new BuyStats[]{BUY_STATS},
            new LastSoldAt[]{LAST_SOLD_AT_ALT_ID});



    public final static Node NODE = new Node(ITEM, MARKET_DATA);

    public final static Node NODE_SAME = new Node(ITEM, MARKET_DATA);

    public final static Node NODE_ALT_ITEM = new Node(ITEM_ALT_ID, MARKET_DATA);

    public final static Node NODE_ALT_MARKET_DATA = new Node(ITEM, MARKET_DATA_ALT_SELL_STATS);



    public final static MarketableItems MARKETABLE_ITEMS = new MarketableItems(List.of(NODE), 1);

    public final static MarketableItems MARKETABLE_ITEMS_SAME = new MarketableItems(List.of(NODE), 1);

    public final static MarketableItems MARKETABLE_ITEMS_ALT_NODES = new MarketableItems(List.of(NODE_ALT_ITEM), 1);

    public final static MarketableItems MARKETABLE_ITEMS_ALT_TOTAL_COUNT = new MarketableItems(List.of(NODE), 99);

}
