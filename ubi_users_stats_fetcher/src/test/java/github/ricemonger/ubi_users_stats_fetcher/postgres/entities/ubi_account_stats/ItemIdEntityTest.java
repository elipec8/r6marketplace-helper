package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemIdEntityTest {

    @Test
    public void equals_should_return_true_if_same() {
        ItemIdEntity entity = new ItemIdEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id_fields() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        ItemIdEntity entity = new ItemIdEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        ItemIdEntity entity1 = new ItemIdEntity();
        entity1.setItemId("itemId");

        ItemIdEntity entity2 = new ItemIdEntity();
        entity2.setItemId("itemId");

        entity1.setItemId("itemId1");
        assertNotEquals(entity1, entity2);
    }
}