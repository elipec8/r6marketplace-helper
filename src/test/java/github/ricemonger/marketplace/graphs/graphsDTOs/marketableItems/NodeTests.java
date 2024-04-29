package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.graphsDTOs.DtoStatics.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class NodeTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(NODE, NODE_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(NODE,NODE_ALT_ITEM);
        assertNotEquals(NODE,NODE_ALT_MARKET_DATA);
    }
}