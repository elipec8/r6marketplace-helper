package github.ricemonger.trades_manager.postgres.entities.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValueEntityTest {
    @Test
    public void equals_should_return_true_if_same() {
        TagValueEntity tag = new TagValueEntity();
        assertEquals(tag, tag);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue");

        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        TagValueEntity tag = new TagValueEntity();
        assertNotEquals(null, tag);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue1");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue2");

        assertNotEquals(tag1, tag2);
    }

    @Test
    public void isFullyEqual_should_return_true_if_same() {
        TagValueEntity tag = new TagValueEntity();
        assertTrue(tag.isFullyEqual(tag));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue");

        assertTrue(tag1.isFullyEqual(tag2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue1");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue1");

        tag1.setValue("tagValue2");
        assertFalse(tag1.isFullyEqual(tag2));
    }
}