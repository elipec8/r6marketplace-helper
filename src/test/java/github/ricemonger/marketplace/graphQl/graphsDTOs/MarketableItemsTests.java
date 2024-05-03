package github.ricemonger.marketplace.graphQl.graphsDTOs;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphQl.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MarketableItemsTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(MARKETABLE_ITEMS, MARKETABLE_ITEMS_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(MARKETABLE_ITEMS, MARKETABLE_ITEMS_ALT_NODES);
        assertNotEquals(MARKETABLE_ITEMS, MARKETABLE_ITEMS_ALT_TOTAL_COUNT);
    }
}