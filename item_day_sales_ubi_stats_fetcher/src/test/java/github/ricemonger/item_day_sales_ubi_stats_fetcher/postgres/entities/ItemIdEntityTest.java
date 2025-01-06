package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemIdEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_ids() {
        ItemEntity entity1 = new ItemEntity();
        entity1.setItemId("itemId");

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        ItemEntity entity = new ItemEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        ItemEntity entity1 = new ItemEntity();
        entity1.setItemId("itemId");

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemEntity entity = new ItemEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        ItemEntity entity1 = new ItemEntity();
        entity1.setItemId("itemId1");

        ItemEntity entity2 = new ItemEntity();
        entity2.setItemId("itemId2");

        assertNotEquals(entity1, entity2);
    }
}