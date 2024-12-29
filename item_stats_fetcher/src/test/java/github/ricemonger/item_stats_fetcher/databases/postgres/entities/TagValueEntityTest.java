package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagValueEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_ids() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue");

        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

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
    public void isFullyEqual_should_return_false_if_null() {
        TagValueEntity tag = new TagValueEntity();
        assertFalse(tag.isFullyEqual(null));
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