package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.Tag;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.IsOwnedFilter;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemFilterEntityTest {

    @Test
    public void toItemFilterEntity_with_user_should_properly_map_with_valid_fields() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("1");
        itemFilter.setFilterType(FilterType.ALLOW);
        itemFilter.setIsOwned(IsOwnedFilter.OWNED);
        itemFilter.setItemNamePatterns(List.of("pattern1", "pattern2"));
        itemFilter.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        Tag tag1 = new Tag("value1", "name1", TagGroup.Rarity);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Rarity);
        itemFilter.setTags(List.of(tag1, tag2));
        itemFilter.setMinSellPrice(1);
        itemFilter.setMaxBuyPrice(2);
        itemFilter.setMinLastSoldPrice(3);
        itemFilter.setMaxLastSoldPrice(4);

        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setUser(user);
        expected.setName("1");
        expected.setFilterType(FilterType.ALLOW);
        expected.setIsOwned(IsOwnedFilter.OWNED);
        expected.setItemNamePatterns("pattern1,pattern2");
        expected.setItemTypes("Charm,WeaponSkin");
        expected.setTags(Set.of(new TagEntity(tag1), new TagEntity(tag2)));
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilterEntity actual = new ItemFilterEntity(user, itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilterEntity_with_user_should_properly_map_with_valid_fields_and_empty_lists() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("1");
        itemFilter.setFilterType(FilterType.DENY);
        itemFilter.setIsOwned(IsOwnedFilter.NOT_OWNED);
        itemFilter.setItemNamePatterns(List.of());
        itemFilter.setItemTypes(List.of());
        itemFilter.setTags(List.of());
        itemFilter.setMinSellPrice(1);
        itemFilter.setMaxBuyPrice(2);
        itemFilter.setMinLastSoldPrice(3);
        itemFilter.setMaxLastSoldPrice(4);

        UserEntity user = new UserEntity();
        user.setId(1L);

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setUser(user);
        expected.setName("1");
        expected.setFilterType(FilterType.DENY);
        expected.setIsOwned(IsOwnedFilter.NOT_OWNED);
        expected.setItemNamePatterns("");
        expected.setItemTypes("");
        expected.setTags(Set.of());
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilterEntity actual = new ItemFilterEntity(user, itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilterEntity_with_user_should_properly_map_with_null_fields() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName(null);
        itemFilter.setFilterType(null);
        itemFilter.setIsOwned(null);
        itemFilter.setItemNamePatterns(null);
        itemFilter.setItemTypes(null);
        itemFilter.setTags(null);
        itemFilter.setMinSellPrice(null);
        itemFilter.setMaxBuyPrice(null);
        itemFilter.setMinLastSoldPrice(null);
        itemFilter.setMaxLastSoldPrice(null);

        UserEntity user = null;

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setUser(null);
        expected.setName(null);
        expected.setFilterType(null);
        expected.setIsOwned(null);
        expected.setItemNamePatterns("");
        expected.setItemTypes("");
        expected.setTags(Set.of());
        expected.setMinSellPrice(null);
        expected.setMaxBuyPrice(null);
        expected.setMinLastSoldPrice(null);
        expected.setMaxLastSoldPrice(null);

        ItemFilterEntity actual = new ItemFilterEntity(user, itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilterEntity_should_properly_map_with_valid_fields() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("1");
        itemFilter.setFilterType(FilterType.ALLOW);
        itemFilter.setIsOwned(IsOwnedFilter.OWNED);
        itemFilter.setItemNamePatterns(List.of("pattern1", "pattern2"));
        itemFilter.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        Tag tag1 = new Tag("value1", "name1", TagGroup.Rarity);
        Tag tag2 = new Tag("value2", "name2", TagGroup.Rarity);
        itemFilter.setTags(List.of(tag1, tag2));
        itemFilter.setMinSellPrice(1);
        itemFilter.setMaxBuyPrice(2);
        itemFilter.setMinLastSoldPrice(3);
        itemFilter.setMaxLastSoldPrice(4);

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setName("1");
        expected.setFilterType(FilterType.ALLOW);
        expected.setIsOwned(IsOwnedFilter.OWNED);
        expected.setItemNamePatterns("pattern1,pattern2");
        expected.setItemTypes("Charm,WeaponSkin");
        expected.setTags(Set.of(new TagEntity(tag1), new TagEntity(tag2)));
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilterEntity actual = new ItemFilterEntity(itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilterEntity_should_properly_map_with_valid_fields_and_empty_lists() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("1");
        itemFilter.setFilterType(FilterType.DENY);
        itemFilter.setIsOwned(IsOwnedFilter.NOT_OWNED);
        itemFilter.setItemNamePatterns(List.of());
        itemFilter.setItemTypes(List.of());
        itemFilter.setTags(List.of());
        itemFilter.setMinSellPrice(1);
        itemFilter.setMaxBuyPrice(2);
        itemFilter.setMinLastSoldPrice(3);
        itemFilter.setMaxLastSoldPrice(4);

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setName("1");
        expected.setFilterType(FilterType.DENY);
        expected.setIsOwned(IsOwnedFilter.NOT_OWNED);
        expected.setItemNamePatterns("");
        expected.setItemTypes("");
        expected.setTags(Set.of());
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilterEntity actual = new ItemFilterEntity(itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilterEntity_should_properly_map_with_null_fields() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName(null);
        itemFilter.setFilterType(null);
        itemFilter.setIsOwned(null);
        itemFilter.setItemNamePatterns(null);
        itemFilter.setItemTypes(null);
        itemFilter.setTags(null);
        itemFilter.setMinSellPrice(null);
        itemFilter.setMaxBuyPrice(null);
        itemFilter.setMinLastSoldPrice(null);
        itemFilter.setMaxLastSoldPrice(null);

        ItemFilterEntity expected = new ItemFilterEntity();
        expected.setName(null);
        expected.setFilterType(null);
        expected.setIsOwned(null);
        expected.setItemNamePatterns("");
        expected.setItemTypes("");
        expected.setTags(Set.of());
        expected.setMinSellPrice(null);
        expected.setMaxBuyPrice(null);
        expected.setMinLastSoldPrice(null);
        expected.setMaxLastSoldPrice(null);

        ItemFilterEntity actual = new ItemFilterEntity(itemFilter);

        assertTrue(entitiesAreEqual(expected, actual));
    }

    @Test
    public void toItemFilter_should_properly_map_with_valid_fields() {
        ItemFilterEntity entity = new ItemFilterEntity();
        entity.setName("1");
        entity.setFilterType(FilterType.ALLOW);
        entity.setIsOwned(IsOwnedFilter.OWNED);
        entity.setItemNamePatterns(String.join(",", "pattern1", "pattern2"));
        entity.setItemTypes(String.join(",", ItemType.Charm.name(), ItemType.WeaponSkin.name()));
        Tag tag1 = new Tag("value1", "name1", TagGroup.Rarity);
        entity.setTags(Set.of(new TagEntity(tag1)));
        entity.setMinSellPrice(1);
        entity.setMaxBuyPrice(2);
        entity.setMinLastSoldPrice(3);
        entity.setMaxLastSoldPrice(4);

        ItemFilter expected = new ItemFilter();
        expected.setName("1");
        expected.setFilterType(FilterType.ALLOW);
        expected.setIsOwned(IsOwnedFilter.OWNED);
        expected.setItemNamePatterns(List.of("pattern1", "pattern2"));
        expected.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        expected.setTags(List.of(tag1));
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilter actual = entity.toItemFilter();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemFilter_should_properly_map_with_valid_fields_and_empty_lists() {
        ItemFilterEntity entity = new ItemFilterEntity();
        entity.setName("1");
        entity.setFilterType(FilterType.DENY);
        entity.setIsOwned(IsOwnedFilter.NOT_OWNED);
        entity.setItemNamePatterns("");
        entity.setItemTypes("");
        entity.setTags(Set.of());
        entity.setMinSellPrice(1);
        entity.setMaxBuyPrice(2);
        entity.setMinLastSoldPrice(3);
        entity.setMaxLastSoldPrice(4);

        ItemFilter expected = new ItemFilter();
        expected.setName("1");
        expected.setFilterType(FilterType.DENY);
        expected.setIsOwned(IsOwnedFilter.NOT_OWNED);
        expected.setItemNamePatterns(List.of());
        expected.setItemTypes(List.of());
        expected.setTags(List.of());
        expected.setMinSellPrice(1);
        expected.setMaxBuyPrice(2);
        expected.setMinLastSoldPrice(3);
        expected.setMaxLastSoldPrice(4);

        ItemFilter actual = entity.toItemFilter();

        assertEquals(expected, actual);
    }

    @Test
    public void toItemFilter_should_properly_map_with_null_fields() {
        ItemFilterEntity entity = new ItemFilterEntity();
        entity.setName(null);
        entity.setFilterType(null);
        entity.setIsOwned(null);
        entity.setItemNamePatterns(null);
        entity.setItemTypes(null);
        entity.setTags(null);
        entity.setMinSellPrice(null);
        entity.setMaxBuyPrice(null);
        entity.setMinLastSoldPrice(null);
        entity.setMaxLastSoldPrice(null);

        ItemFilter expected = new ItemFilter();
        expected.setName(null);
        expected.setFilterType(null);
        expected.setIsOwned(null);
        expected.setItemNamePatterns(List.of());
        expected.setItemTypes(List.of());
        expected.setTags(List.of());
        expected.setMinSellPrice(null);
        expected.setMaxBuyPrice(null);
        expected.setMinLastSoldPrice(null);
        expected.setMaxLastSoldPrice(null);

        ItemFilter actual = entity.toItemFilter();

        assertEquals(expected, actual);
    }

    private boolean entitiesAreEqual(ItemFilterEntity entity1, ItemFilterEntity entity2) {
        return entity1 == entity2 || (entity1 != null && entity2 != null &&
                                      Objects.equals(entity1.getUser(), entity2.getUser()) &&
                                      Objects.equals(entity1.getName(), entity2.getName()) &&
                                      entity1.getFilterType() == entity2.getFilterType() &&
                                      entity1.getIsOwned() == entity2.getIsOwned() &&
                                      Objects.equals(entity1.getItemNamePatterns(), entity2.getItemNamePatterns()) &&
                                      Objects.equals(entity1.getItemTypes(), entity2.getItemTypes()) &&
                                      Objects.equals(entity1.getMinSellPrice(), entity2.getMinSellPrice()) &&
                                      Objects.equals(entity1.getMaxBuyPrice(), entity2.getMaxBuyPrice()) &&
                                      Objects.equals(entity1.getMinLastSoldPrice(), entity2.getMinLastSoldPrice()) &&
                                      Objects.equals(entity1.getMaxLastSoldPrice(), entity2.getMaxLastSoldPrice()));
    }
}