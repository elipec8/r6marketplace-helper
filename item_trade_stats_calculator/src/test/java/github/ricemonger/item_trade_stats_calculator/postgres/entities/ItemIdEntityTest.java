package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemIdEntityTest {
    @Test
    public void hashCode_should_be_equal_for_equal_ids() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemIdEntity entity = new ItemIdEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        ItemIdEntity entity = new ItemIdEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId1");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId2");

        assertNotEquals(entity1, entity2);
    }
}