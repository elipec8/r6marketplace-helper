package github.ricemonger.trades_manager.postgres.entities.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemIdEntityTest {

    @Test
    public void equals_should_return_false_for_null() {
        assertNotEquals(null, new ItemIdEntity());
    }

    @Test
    public void equals_should_return_false_for_different_class() {
        assertNotEquals(new ItemIdEntity(), new Object());
    }

    @Test
    public void equals_should_return_true_for_same_object() {
        ItemIdEntity item = new ItemIdEntity();
        assertEquals(item, item);
    }

    @Test
    public void equals_should_return_true_for_same_id() {
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("1");
        assertEquals(item1, item2);
    }

    @Test
    public void equals_should_return_false_for_different_id() {
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("2");
        assertNotEquals(item1, item2);
    }
}