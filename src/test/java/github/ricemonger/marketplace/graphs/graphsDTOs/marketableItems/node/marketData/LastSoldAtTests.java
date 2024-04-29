package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LastSoldAtTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(LAST_SOLD_AT, LAST_SOLD_AT_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_ID);
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_PRICE);
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_PREFORMED_AT);
    }
}