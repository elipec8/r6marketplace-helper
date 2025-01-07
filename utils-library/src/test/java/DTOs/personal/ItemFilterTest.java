package DTOs.personal;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.Tag;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ItemFilterTest {

    @Test
    public void static_filterItems_should_properly_filter_items_by_multiple_filters_for_allow_and_deny() {
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);
        Item item4 = mock(Item.class);
        Item item5 = mock(Item.class);
        Item item6 = mock(Item.class);
        Item item7 = mock(Item.class);
        Item item8 = mock(Item.class);
        Item item9 = mock(Item.class);
        Item item10 = mock(Item.class);
        Item item11 = mock(Item.class);
        Item item12 = mock(Item.class);
        Item item13 = mock(Item.class);
        Item item14 = mock(Item.class);
        Item item15 = mock(Item.class);

        Collection<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
                item11, item12, item13, item14, item15);

        Collection<Item> expected = List.of(item1, item12, item13);

        ItemFilter allowFilter1 = mock(ItemFilter.class);
        when(allowFilter1.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter1.filterItems(any())).thenReturn(List.of(item1, item2, item3, item4, item5));

        ItemFilter allowFilter2 = mock(ItemFilter.class);
        when(allowFilter2.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter2.filterItems(any())).thenReturn(List.of(item6, item7, item8, item9));

        ItemFilter allowFilter3 = mock(ItemFilter.class);
        when(allowFilter3.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter3.filterItems(any())).thenReturn(List.of(item11, item12, item13, item14));

        ItemFilter denyFilter1 = mock(ItemFilter.class);
        when(denyFilter1.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter1.filterItems(any())).thenReturn(List.of(item2, item3, item4, item5, item6));

        ItemFilter denyFilter2 = mock(ItemFilter.class);
        when(denyFilter2.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter2.filterItems(any())).thenReturn(List.of(item7, item8, item9));

        ItemFilter denyFilter3 = mock(ItemFilter.class);
        when(denyFilter3.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter3.filterItems(any())).thenReturn(List.of(item11, item14));

        Collection<Item> result = ItemFilter.filterItems(items, List.of(allowFilter1, allowFilter2, allowFilter3, denyFilter1, denyFilter2, denyFilter3));

        System.out.println("Result:");
        for (Item item : result) {
            System.out.println(item);
        }

        assertEquals(result.size(), expected.size());
        assertTrue(result.stream().allMatch(res -> expected.stream().anyMatch(ex -> res == ex)));
    }

    @Test
    public void static_filterItems_should_properly_filter_items_by_multiple_filters_for_allow() {
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);
        Item item4 = mock(Item.class);
        Item item5 = mock(Item.class);
        Item item6 = mock(Item.class);
        Item item7 = mock(Item.class);
        Item item8 = mock(Item.class);
        Item item9 = mock(Item.class);
        Item item10 = mock(Item.class);
        Item item11 = mock(Item.class);
        Item item12 = mock(Item.class);
        Item item13 = mock(Item.class);
        Item item14 = mock(Item.class);
        Item item15 = mock(Item.class);

        Collection<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
                item11, item12, item13, item14, item15);

        Collection<Item> expected = List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item11, item12, item13, item14);

        ItemFilter allowFilter1 = mock(ItemFilter.class);
        when(allowFilter1.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter1.filterItems(any())).thenReturn(List.of(item1, item2, item3, item4, item5));

        ItemFilter allowFilter2 = mock(ItemFilter.class);
        when(allowFilter2.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter2.filterItems(any())).thenReturn(List.of(item6, item7, item8, item9));

        ItemFilter allowFilter3 = mock(ItemFilter.class);
        when(allowFilter3.getFilterType()).thenReturn(FilterType.ALLOW);
        when(allowFilter3.filterItems(any())).thenReturn(List.of(item11, item12, item13, item14));

        Collection<Item> result = ItemFilter.filterItems(items, List.of(allowFilter1, allowFilter2, allowFilter3));

        System.out.println("Result:");
        for (Item item : result) {
            System.out.println(item);
        }

        assertEquals(result.size(), expected.size());
        assertTrue(result.stream().allMatch(res -> expected.stream().anyMatch(ex -> res == ex)));
    }

    @Test
    public void static_filterItems_should_properly_filter_items_by_multiple_filters_for_deny() {
        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);
        Item item3 = mock(Item.class);
        Item item4 = mock(Item.class);
        Item item5 = mock(Item.class);
        Item item6 = mock(Item.class);
        Item item7 = mock(Item.class);
        Item item8 = mock(Item.class);
        Item item9 = mock(Item.class);
        Item item10 = mock(Item.class);
        Item item11 = mock(Item.class);
        Item item12 = mock(Item.class);
        Item item13 = mock(Item.class);
        Item item14 = mock(Item.class);
        Item item15 = mock(Item.class);

        Collection<Item> items = List.of(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10,
                item11, item12, item13, item14, item15);

        Collection<Item> expected = List.of(item1, item12, item13, item15);

        ItemFilter denyFilter1 = mock(ItemFilter.class);
        when(denyFilter1.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter1.filterItems(any())).thenReturn(List.of(item2, item3, item4, item5, item6));

        ItemFilter denyFilter2 = mock(ItemFilter.class);
        when(denyFilter2.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter2.filterItems(any())).thenReturn(List.of(item7, item8, item9, item10));

        ItemFilter denyFilter3 = mock(ItemFilter.class);
        when(denyFilter3.getFilterType()).thenReturn(FilterType.DENY);
        when(denyFilter3.filterItems(any())).thenReturn(List.of(item11, item14));

        Collection<Item> result = ItemFilter.filterItems(items, List.of(denyFilter1, denyFilter2, denyFilter3));

        System.out.println("Result:");
        for (Item item : result) {
            System.out.println(item);
        }

        assertEquals(result.size(), expected.size());
        assertTrue(result.stream().allMatch(res -> expected.stream().anyMatch(ex -> res == ex)));
    }

    @Test
    public void filterItems_should_return_list_by_filter_parameters() {
        Collection<Item> expected = new ArrayList<>();
        expected.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 0));
        expected.add(createItem("test2", ItemType.WeaponSkin, List.of("value1", "value2", "value3"), 110, 190, 0));
        expected.add(createItem("test1111", ItemType.WeaponSkin, List.of("value1", "value2"), 110, 190, 0));

        Collection<Item> items = new ArrayList<>(expected);
        items.add(createItem("test2", ItemType.WeaponSkin, List.of("value1"), 110, 190, 0));
        items.add(createItem("test3", ItemType.WeaponSkin, List.of("value1", "value2"), 100, 200, 0));
        items.add(createItem("test1", ItemType.CharacterHeadgear, List.of("value1", "value2"), 100, 200, 0));
        items.add(createItem("test1", ItemType.Charm, List.of(), 100, 200, 0));
        items.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 90, 200, 0));
        items.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 210, 0));

        ItemFilter filter = new ItemFilter();
        filter.setItemNamePatterns(List.of("test1", "test2"));
        filter.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        filter.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        filter.setMinSellPrice(100);
        filter.setMaxBuyPrice(200);

        Collection<Item> result = filter.filterItems(items);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }

    private Item createItem(String name, ItemType type, List<String> tagValues, int minSellPrice, int maxBuyPrice, int lastSoldPrice) {
        Item item = new Item();
        item.setItemId(name);
        item.setName(name);
        item.setType(type);
        item.setTags(tagValues);
        item.setMinSellPrice(minSellPrice);
        item.setMaxBuyPrice(maxBuyPrice);
        item.setLastSoldPrice(lastSoldPrice);
        return item;
    }

    @Test
    public void getItemNamePatternsAsString_should_map_from_list_to_string() {
        ItemFilter filter = new ItemFilter();
        filter.setItemNamePatterns(List.of("test1", "test2", "test3"));

        assertEquals("test1, test2, test3", filter.getItemNamePatternsAsString());

        filter.setItemNamePatterns(new ArrayList<>());

        assertEquals("", filter.getItemNamePatternsAsString());

        filter.setItemNamePatterns(null);

        assertEquals("", filter.getItemNamePatternsAsString());
    }

    @Test
    public void setItemNamePatternsFromString_should_map_from_string_to_list() {
        ItemFilter filter = new ItemFilter();
        filter.setItemNamePatternsFromString(" test1 , test2 , test3 ");

        assertEquals(List.of("test1", "test2", "test3"), filter.getItemNamePatterns());

        filter.setItemNamePatternsFromString("test1, test2 ,test3");

        assertEquals(List.of("test1", "test2", "test3"), filter.getItemNamePatterns());

        filter.setItemNamePatternsFromString("test1,test2,test3");

        assertEquals(List.of("test1", "test2", "test3"), filter.getItemNamePatterns());

        filter.setItemNamePatternsFromString("");

        assertEquals(new ArrayList<>(), filter.getItemNamePatterns());

        filter.setItemNamePatternsFromString(null);

        assertEquals(new ArrayList<>(), filter.getItemNamePatterns());
    }

    @Test
    public void getItemTypesAsString_should_map_from_list_to_ItemTypes() {
        ItemFilter filter = new ItemFilter();
        filter.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin, ItemType.DroneSkin));

        assertEquals("Charm, WeaponSkin, DroneSkin", filter.getItemTypesAsString());

        filter.setItemTypes(new ArrayList<>());

        assertEquals("", filter.getItemTypesAsString());

        filter.setItemTypes(null);

        assertEquals("", filter.getItemTypesAsString());
    }

    @Test
    public void setItemTypesFromString_should_map_from_string_to_list() {
        ItemFilter filter = new ItemFilter();
        filter.setItemTypesFromString(" Charm , WeaponSkin , DroneSkin ");

        assertEquals(List.of(ItemType.Charm, ItemType.WeaponSkin, ItemType.DroneSkin), filter.getItemTypes());

        filter.setItemTypesFromString("Charm, WeaponSkin ,DroneSkin");

        assertEquals(List.of(ItemType.Charm, ItemType.WeaponSkin, ItemType.DroneSkin), filter.getItemTypes());

        filter.setItemTypesFromString("Charm,WeaponSkin,DroneSkin");

        assertEquals(List.of(ItemType.Charm, ItemType.WeaponSkin, ItemType.DroneSkin), filter.getItemTypes());

        filter.setItemTypesFromString("");

        assertEquals(new ArrayList<>(), filter.getItemTypes());

        filter.setItemTypesFromString(null);

        assertEquals(new ArrayList<>(), filter.getItemTypes());
    }

    @Test
    public void addTags_should_add_all_tags() {
        List<Tag> initialTags = new ArrayList<>();
        initialTags.add(new Tag("value1", "name1", TagGroup.Rarity));

        ItemFilter filter = new ItemFilter();
        filter.setTags(initialTags);


        List<Tag> added = new ArrayList<>();
        added.add(new Tag("value2", "name2", TagGroup.Rarity));
        added.add(new Tag("value3", "name3", TagGroup.Rarity));

        List<Tag> expected = new ArrayList<>();
        expected.addAll(initialTags);
        expected.addAll(added);

        filter.addTags(added);
        List<Tag> result = filter.getTags();

        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }

    @Test
    public void toString_should_not_throw_with_null_fields() {
        ItemFilter filter = new ItemFilter();
        filter.setItemNamePatterns(null);
        filter.setItemTypes(null);
        filter.setTags(null);
        filter.setMinSellPrice(null);
        filter.setMaxBuyPrice(null);
        filter.setIsOwned(null);
        filter.setFilterType(null);
        filter.setName(null);

        assertDoesNotThrow(filter::toString);
    }
}