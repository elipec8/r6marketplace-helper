package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.database.neo4j.entities.EntitiesStatics.*;
import static org.junit.jupiter.api.Assertions.*;

class ItemEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(ITEM, ITEM_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferentFields(){
        assertNotEquals(ITEM, ITEM_ALT_ITEM_FULL_ID);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_NAME);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_DESCRIPTION);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_TAGS);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_TYPE);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_SELL_STATS);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_BUY_STATS);
        assertNotEquals(ITEM, ITEM_ALT_ITEM_LAST_SOLD_AT);
    }
}