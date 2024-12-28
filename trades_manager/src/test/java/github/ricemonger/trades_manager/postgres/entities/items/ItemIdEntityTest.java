package github.ricemonger.trades_manager.postgres.entities.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemIdEntityTest {

    @Test
    public void isEqual_should_return_false_for_null(){
        assertFalse(new ItemIdEntity().isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_for_different_class(){
        assertFalse(new ItemIdEntity().isEqual(new Object()));
    }

    @Test
    public void isEqual_should_return_true_for_same_object(){
        ItemIdEntity item = new ItemIdEntity();
        assertTrue(item.isEqual(item));
    }

    @Test
    public void isEqual_should_return_true_for_same_id(){
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("1");
        assertTrue(item1.isEqual(item2));
    }

    @Test
    public void isEqual_should_return_false_for_different_id(){
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("2");
        assertFalse(item1.isEqual(item2));
    }

    @Test
    public void isFullyEqual_should_return_false_for_null(){
        assertFalse(new ItemIdEntity().isFullyEqual(null));
    }

    @Test
    public void isFullyEqual_should_return_false_for_different_class(){
        assertFalse(new ItemIdEntity().isFullyEqual(new Object()));
    }

    @Test
    public void isFullyEqual_should_return_true_for_same_object(){
        ItemIdEntity item = new ItemIdEntity();
        assertTrue(item.isFullyEqual(item));
    }

    @Test
    public void isFullyEqual_should_return_true_for_same_id(){
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("1");
        assertTrue(item1.isFullyEqual(item2));
    }

    @Test
    public void isFullyEqual_should_return_false_for_different_id(){
        ItemIdEntity item1 = new ItemIdEntity("1");
        ItemIdEntity item2 = new ItemIdEntity("2");
        assertFalse(item1.isFullyEqual(item2));
    }
}