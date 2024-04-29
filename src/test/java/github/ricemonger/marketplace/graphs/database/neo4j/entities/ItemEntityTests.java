package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import github.ricemonger.marketplace.graphs.database.neo4j.enums.ItemType;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemEntityTests {

    public final static SellStatsEntity SELL_STATS = new SellStatsEntity("1", 2, 3, 4);

    public final static SellStatsEntity DIFF_SELL_STATS = new SellStatsEntity("99", 2, 3, 4);

    public final static BuyStatsEntity BUY_STATS = new BuyStatsEntity("1", 2, 3, 4);

    public final static BuyStatsEntity DIFF_BUY_STATS = new BuyStatsEntity("99", 2, 3, 4);

    public final static LastSoldAtEntity LAST_SOLD_AT = new LastSoldAtEntity("1", 2,new Date(3));

    public final static LastSoldAtEntity DIFF_LAST_SOLD_AT = new LastSoldAtEntity("99", 2,new Date(3));

    public final static ItemEntity ITEM = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity SAME_ITEM = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_FULL_ID = new ItemEntity("99", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_NAME = new ItemEntity("1", "99", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_DESCRIPTION = new ItemEntity("1", "2", "99", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_TAGS = new ItemEntity("1", "2", "3", List.of("99"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_TYPE = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterUniform, SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_SELL_STATS = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, DIFF_SELL_STATS, BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_BUY_STATS = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, DIFF_BUY_STATS, LAST_SOLD_AT);

    public final static ItemEntity ALT_ITEM_LAST_SOLD_AT = new ItemEntity("1", "2", "3", List.of("4"), ItemType.CharacterHeadgear, SELL_STATS, BUY_STATS, DIFF_LAST_SOLD_AT);

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(ITEM, SAME_ITEM);
    }

    @Test
    public void equalsShouldReturnFalseForDifferentFields(){
        assertNotEquals(ITEM, ALT_ITEM_FULL_ID);
        assertNotEquals(ITEM, ALT_ITEM_NAME);
        assertNotEquals(ITEM, ALT_ITEM_DESCRIPTION);
        assertNotEquals(ITEM, ALT_ITEM_TAGS);
        assertNotEquals(ITEM, ALT_ITEM_TYPE);
        assertNotEquals(ITEM, ALT_ITEM_SELL_STATS);
        assertNotEquals(ITEM, ALT_ITEM_BUY_STATS);
        assertNotEquals(ITEM, ALT_ITEM_LAST_SOLD_AT);
    }
}