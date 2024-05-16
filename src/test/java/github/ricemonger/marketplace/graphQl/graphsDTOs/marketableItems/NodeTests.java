package github.ricemonger.marketplace.graphQl.graphsDTOs.marketableItems;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphQl.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NodeTests {

    @Test
    public void equalsShouldReturnTrueForSameItemId(){
        assertEquals(NODE, NODE_SAME);
        assertEquals(NODE, NODE_ALT_MARKET_DATA);
    }

    @Test
    public void equalsShouldReturnFalseForNotSameItemId(){
        assertNotEquals(NODE,NODE_ALT_ITEM);
    }
}