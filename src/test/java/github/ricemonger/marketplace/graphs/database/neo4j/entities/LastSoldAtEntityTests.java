package github.ricemonger.marketplace.graphs.database.neo4j.entities;

import org.junit.jupiter.api.Test;

import static github.ricemonger.marketplace.graphs.database.neo4j.entities.EntitiesStatics.*;
import static org.junit.jupiter.api.Assertions.*;

class LastSoldAtEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        assertEquals(LAST_SOLD_AT, LAST_SOLD_AT_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_ID);
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_PRICE);
        assertNotEquals(LAST_SOLD_AT, LAST_SOLD_AT_ALT_PERFORMED_AT);
    }
}