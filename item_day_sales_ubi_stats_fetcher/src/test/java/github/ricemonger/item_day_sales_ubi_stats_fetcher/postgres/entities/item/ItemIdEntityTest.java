package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemIdEntityTest {
    @Test
    public void isEqual_should_return_true_if_same() {
        ItemIdEntity entity = new ItemIdEntity();
        assertTrue(entity.isEqual(entity));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        assertTrue(entity1.isEqual(entity2));
    }

    @Test
    public void isEqual_should_return_false_for_null() {
        ItemIdEntity entity = new ItemIdEntity();
        assertFalse(entity.isEqual(null));
    }


    @Test
    public void isFullyEqual_should_return_true_if_same() {
        ItemIdEntity entity = new ItemIdEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId1");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");
        ;

        assertFalse(entity1.isFullyEqual(entity2));
    }
}