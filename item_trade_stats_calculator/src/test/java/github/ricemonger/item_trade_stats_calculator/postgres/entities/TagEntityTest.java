package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TagEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_same_ids() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue");
        tag1.setName("tagName");
        tag1.setTagGroup(TagGroup.Season);

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue");

        assertEquals(tag1.hashCode(), tag2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        TagEntity tag = new TagEntity();
        assertEquals(tag, tag);
    }

    @Test
    public void equals_should_return_true_if_equal_ids() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue");
        tag1.setName("tagName");
        tag1.setTagGroup(TagGroup.Season);

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue");

        assertEquals(tag1, tag2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        TagEntity tag = new TagEntity();
        assertNotEquals(null, tag);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue1");

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue2");

        assertNotEquals(tag1, tag2);
    }
}