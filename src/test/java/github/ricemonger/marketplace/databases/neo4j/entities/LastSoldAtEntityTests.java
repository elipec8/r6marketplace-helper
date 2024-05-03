package github.ricemonger.marketplace.databases.neo4j.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LastSoldAtEntityTests {

    @Test
    public void equalsShouldReturnTrueForSame(){
        Assertions.assertEquals(EntitiesStatics.LAST_SOLD_AT, EntitiesStatics.LAST_SOLD_AT_SAME);
    }

    @Test
    public void equalsShouldReturnFalseForDifferent(){
        Assertions.assertNotEquals(EntitiesStatics.LAST_SOLD_AT, EntitiesStatics.LAST_SOLD_AT_ALT_ID);
        Assertions.assertNotEquals(EntitiesStatics.LAST_SOLD_AT, EntitiesStatics.LAST_SOLD_AT_ALT_PRICE);
        Assertions.assertNotEquals(EntitiesStatics.LAST_SOLD_AT, EntitiesStatics.LAST_SOLD_AT_ALT_PERFORMED_AT);
    }
}