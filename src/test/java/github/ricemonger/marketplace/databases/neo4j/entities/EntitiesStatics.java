package github.ricemonger.marketplace.databases.neo4j.entities;

import github.ricemonger.marketplace.databases.neo4j.enums.ItemType;

import java.util.Date;
import java.util.List;

public class EntitiesStatics {

    public final static SellStatsEntity SELL_STATS = new SellStatsEntity("1", 2, 3, 4);

    public final static SellStatsEntity SELL_STATS_SAME = new SellStatsEntity("1", 2, 3, 4);

    public final static SellStatsEntity SELL_STATS_ALT_ID = new SellStatsEntity("99", 2, 3, 4);

    public final static SellStatsEntity SELL_STATS_ALT_LOWER_PRICE = new SellStatsEntity("1", 99, 3, 4);

    public final static SellStatsEntity SELL_STATS_ALT_HIGHEST_PRICE = new SellStatsEntity("1", 2, 99, 4);

    public final static SellStatsEntity SELL_STATS_ALT_ACTIVE_COUNT = new SellStatsEntity("1", 2, 3, 99);



    public final static BuyStatsEntity BUY_STATS = new BuyStatsEntity("1", 2, 3, 4);

    public final static BuyStatsEntity BUY_STATS_SAME = new BuyStatsEntity("1", 2, 3, 4);

    public final static BuyStatsEntity BUY_STATS_ALT_ID = new BuyStatsEntity("99", 2, 3, 4);

    public final static BuyStatsEntity BUY_STATS_ALT_LOWER_PRICE = new BuyStatsEntity("1", 99, 3, 4);

    public final static BuyStatsEntity BUY_STATS_ALT_HIGHEST_PRICE = new BuyStatsEntity("1", 2, 99, 4);

    public final static BuyStatsEntity BUY_STATS_ALT_ACTIVE_COUNT = new BuyStatsEntity("1", 2, 3, 99);



    public final static LastSoldAtEntity LAST_SOLD_AT = new LastSoldAtEntity("1", 2,new Date(3));

    public final static LastSoldAtEntity LAST_SOLD_AT_SAME = new LastSoldAtEntity("1", 2,new Date(3));

    public final static LastSoldAtEntity LAST_SOLD_AT_ALT_ID = new LastSoldAtEntity("99", 2,new Date(3));

    public final static LastSoldAtEntity LAST_SOLD_AT_ALT_PRICE = new LastSoldAtEntity("1", 99,new Date(3));

    public final static LastSoldAtEntity LAST_SOLD_AT_ALT_PERFORMED_AT = new LastSoldAtEntity("1", 2,new Date(99));



    public final static ItemEntity ITEM = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_SAME = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_FULL_ID = new ItemEntity("99", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_NAME = new ItemEntity("1", "99", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_DESCRIPTION = new ItemEntity("1", "2", "99", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_TAGS = new ItemEntity("1", "2", "3", List.of("99"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_TYPE = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterUniform, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_SELL_STATS = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS_ALT_ID, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_BUY_STATS = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS_ALT_ID, LAST_SOLD_AT);

    public final static ItemEntity ITEM_ALT_ITEM_LAST_SOLD_AT = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT_ALT_ID);
}
