package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class LastSoldAtEntityTests {

    public final static LastSoldAtEntity LAST_SOLD_AT = new LastSoldAtEntity("1", 2,new Date(3));

    public final static LastSoldAtEntity SAME_LAST_SOLD_AT = new LastSoldAtEntity("1", 2,new Date(3));

    public final static LastSoldAtEntity ALT_ID = new LastSoldAtEntity("99", 2,new Date(3));

    public final static LastSoldAtEntity ALT_PRICE = new LastSoldAtEntity("1", 99,new Date(3));

    public final static LastSoldAtEntity ALT_PERFORMED_AT = new LastSoldAtEntity("1", 2,new Date(99));

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