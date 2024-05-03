package github.ricemonger.marketplace.databases.neo4j.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ItemEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        Assertions.assertEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferentFields(){
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_FULL_ID);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_NAME);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_DESCRIPTION);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_TAGS);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_TYPE);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_SELL_STATS);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_BUY_STATS);
        Assertions.assertNotEquals(EntitiesStatics.ITEM, EntitiesStatics.ITEM_ALT_ITEM_LAST_SOLD_AT);
    }
}