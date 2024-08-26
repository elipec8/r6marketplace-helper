package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagEntityTest {

    @Test
    public void toTagEntity_should_properly_map_with_valid_fields() {
        Tag tag = new Tag();
        tag.setValue("value");
        tag.setName("name");
        tag.setTagGroup(TagGroup.Rarity);

        TagEntity expected = new TagEntity();
        expected.setValue("value");
        expected.setName("name");
        expected.setTagGroup(TagGroup.Rarity);

        TagEntity actual = new TagEntity(tag);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toTagEntity_should_properly_map_with_null_fields() {
        Tag tag = new Tag();
        tag.setValue(null);
        tag.setName(null);
        tag.setTagGroup(null);

        TagEntity expected = new TagEntity();
        expected.setValue(null);
        expected.setName(null);
        expected.setTagGroup(null);

        TagEntity actual = new TagEntity(tag);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toTag_should_properly_map_with_valid_fields() {
        TagEntity entity = new TagEntity();
        entity.setValue("value");
        entity.setName("name");
        entity.setTagGroup(TagGroup.Rarity);

        Tag expected = new Tag();
        expected.setValue("value");
        expected.setName("name");
        expected.setTagGroup(TagGroup.Rarity);

        Tag actual = entity.toTag();

        assertEquals(expected, actual);
    }

    @Test
    public void toTag_should_properly_map_with_null_fields() {
        TagEntity entity = new TagEntity();
        entity.setValue(null);
        entity.setName(null);
        entity.setTagGroup(null);

        Tag expected = new Tag();
        expected.setValue(null);
        expected.setName(null);
        expected.setTagGroup(null);

        Tag actual = entity.toTag();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(TagEntity entity1, TagEntity entity2) {
        return Objects.equals(entity1.getValue(), entity2.getValue()) &&
               Objects.equals(entity1.getName(), entity2.getName()) &&
               Objects.equals(entity1.getTagGroup(), entity2.getTagGroup());
    }
}