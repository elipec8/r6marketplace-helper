package github.ricemonger.marketplace.graphQl.mappers;

import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.Marketplace;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.TagGroup;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Tags;
import github.ricemonger.marketplace.graphQl.dtos.config_query_marketplace.marketplace.Type;
import github.ricemonger.utils.dtos.Tag;
import github.ricemonger.utils.exceptions.GraphQlConfigQueryMarketplaceMappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConfigQueryMarketplaceMapperTest {
    @Autowired
    private ConfigQueryMarketplaceMapper configQueryMarketplaceMapper;

    @Test
    public void mapTags_should_map_tags_with_valid_fields() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags("value3", "displayName3"));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(List.of("value3"), "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        List<Tag> expected = new ArrayList<>();
        expected.add(new Tag("value1", "displayName1", github.ricemonger.utils.enums.TagGroup.Season));
        expected.add(new Tag("value2", "displayName2", github.ricemonger.utils.enums.TagGroup.Season));
        expected.add(new Tag("value3", "displayName3", github.ricemonger.utils.enums.TagGroup.Rarity));
        List<Tag> result = (List<Tag>) configQueryMarketplaceMapper.mapTags(marketplace);

        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void mapTags_should_throw_if_null_marketplace() {
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(null);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tags() {
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(new Marketplace(null, new ArrayList<>(), null));
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tagGroups() {
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(new Marketplace(new ArrayList<>(), null, null));
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tag() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(null);

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(List.of("value3"), "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);
        
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tag_value() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags(null, "displayName3"));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(List.of("value3"), "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tag_displayName() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags("value3", null));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(List.of("value3"), "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tagGroup() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags("value3", "displayName3"));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(null);
        tagGroups.add(new TagGroup(List.of("value3"), "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tagGroup_values() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags("value3", "displayName3"));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(List.of("value3"), null));
        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void mapTags_should_throw_if_null_tagGroup_displayName() {
        List<Tags> tags = new ArrayList<>();
        tags.add(new Tags("value1", "displayName1"));
        tags.add(new Tags("value2", "displayName2"));
        tags.add(new Tags("value3", "displayName3"));

        List<TagGroup> tagGroups = new ArrayList<>();
        tagGroups.add(new TagGroup(List.of("value1", "value2"), "Season"));
        tagGroups.add(new TagGroup(null, "Rarity"));

        Marketplace marketplace = new Marketplace(tags, tagGroups, null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.mapTags(marketplace);
        });
    }

    @Test
    public void checkItemTypes_should_throw_if_null_marketplace() {
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.checkItemTypes(null);
        });
    }

    @Test
    public void checkItemTypes_should_throw_if_null_types() {
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.checkItemTypes(new Marketplace(null, null, null));
        });
    }

    @Test
    public void checkItemTypes_should_throw_if_null_type(){
        List<Type> types = new ArrayList<>();
        types.add(null);

        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.checkItemTypes(new Marketplace(null, null, types));
        });
    }
    
    @Test
    public void checkItemTypes_should_throw_if_null_type_value(){
        List<Type> types = new ArrayList<>();
        types.add(new Type(null));
        
        assertThrows(GraphQlConfigQueryMarketplaceMappingException.class, () -> {
            configQueryMarketplaceMapper.checkItemTypes(new Marketplace(null, null, types));
        });
    }
}