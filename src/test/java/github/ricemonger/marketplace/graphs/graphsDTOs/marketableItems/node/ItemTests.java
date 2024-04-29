package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(ITEM, ITEM_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(ITEM, ITEM_ALT_ID);
        assertNotEquals(ITEM, ITEM_ALT_ASSERT_URL);
        assertNotEquals(ITEM, ITEM_ALT_NAME);
        assertNotEquals(ITEM, ITEM_ALT_TAGS);
        assertNotEquals(ITEM, ITEM_ALT_TYPE);
    }

}