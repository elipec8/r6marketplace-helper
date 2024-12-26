package github.ricemonger.item_stats_fetcher.databases.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValueEntityTest {
    @Test
    public void isEqual_should_return_true_if_same() {
        TagValueEntity tag = new TagValueEntity();
        assertTrue(tag.isEqual(tag));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue");

        assertTrue(tag1.isEqual(tag2));
    }

    @Test
    public void isEqual_should_return_false_if_null() {
        TagValueEntity tag = new TagValueEntity();
        assertFalse(tag.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_different_ids() {
        TagValueEntity tag1 = new TagValueEntity();
        tag1.setValue("tagValue1");

        TagValueEntity tag2 = new TagValueEntity();
        tag2.setValue("tagValue2");

        assertFalse(tag1.isEqual(tag2));
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