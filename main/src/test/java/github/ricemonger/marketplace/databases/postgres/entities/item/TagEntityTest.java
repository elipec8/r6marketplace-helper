package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

class TagEntityTest {

    @Test
    public void isEqual_should_return_true_if_same() {
        TagEntity tag = new TagEntity();
        assertTrue(tag.isEqual(tag));
    }

    @Test
    public void isEqual_should_return_true_if_equal_id() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue");
        tag1.setName("tagName");
        tag1.setTagGroup(TagGroup.Season);

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue");

        assertTrue(tag1.isEqual(tag2));
    }

    @Test
    public void isEqual_should_return_false_if_null() {
        TagEntity tag = new TagEntity();
        assertFalse(tag.isEqual(null));
    }

    @Test
    public void isEqual_should_return_false_if_different_ids() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue1");

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue2");

        assertFalse(tag1.isEqual(tag2));
    }

    @Test
    public void isFullyEqual_should_return_true_if_same() {
        TagEntity tag = new TagEntity();
        assertTrue(tag.isFullyEqual(tag));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue");
        tag1.setName("tagName");
        tag1.setTagGroup(TagGroup.Season);

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue");
        tag2.setName("tagName");
        tag2.setTagGroup(TagGroup.Season);

        assertTrue(tag1.isFullyEqual(tag2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_not_equal() {
        TagEntity tag1 = new TagEntity();
        tag1.setValue("tagValue1");
        tag1.setName("tagName1");
        tag1.setTagGroup(TagGroup.Season);

        TagEntity tag2 = new TagEntity();
        tag2.setValue("tagValue1");
        tag2.setName("tagName1");
        tag2.setTagGroup(TagGroup.Season);

        tag1.setValue("tagValue2");
        assertFalse(tag1.isFullyEqual(tag2));
        tag1.setValue("tagValue1");
        tag1.setName("tagName2");
        assertFalse(tag1.isFullyEqual(tag2));
        tag1.setName("tagName1");
        tag1.setTagGroup(TagGroup.Rarity);
        assertFalse(tag1.isFullyEqual(tag2));
    }
}