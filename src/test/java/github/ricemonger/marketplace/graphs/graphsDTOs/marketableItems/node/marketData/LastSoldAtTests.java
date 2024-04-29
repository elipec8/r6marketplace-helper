package github.ricemonger.marketplace.graphs.graphsDTOs.marketableItems.node.marketData;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class LastSoldAtTests {

    public final static LastSoldAt LAST_SOLD_AT = new LastSoldAt("1",2,"3");

    public final static LastSoldAt SAME_LAST_SOLD_AT = new LastSoldAt("1",2,"3");

    public final static LastSoldAt ALT_ID = new LastSoldAt("99",2,"3");

    public final static LastSoldAt ALT_PRICE = new LastSoldAt("1",99,"3");

    public final static LastSoldAt ALT_PERFORMED_AT = new LastSoldAt("1",2,"99");

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(LAST_SOLD_AT, SAME_LAST_SOLD_AT);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(LAST_SOLD_AT, ALT_ID);
        assertNotEquals(LAST_SOLD_AT, ALT_PRICE);
        assertNotEquals(LAST_SOLD_AT, ALT_PERFORMED_AT);
    }
}