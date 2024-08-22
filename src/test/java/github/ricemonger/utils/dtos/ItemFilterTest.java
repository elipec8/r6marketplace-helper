package github.ricemonger.utils.dtos;

import github.ricemonger.utils.enums.FilterType;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemFilterTest {

    @Test
    public void static_filterItems_should_properly_filter_items_by_multiple_filters(){
        Collection<Item> expected = new ArrayList<>();
        expected.add(createItem("test11", ItemType.WeaponSkin, List.of("value1"), 110, 190, 150));
        expected.add(createItem("test2", ItemType.WeaponSkin, List.of("value1","value3"), 110, 190, 100));
        expected.add(createItem("test3", ItemType.WeaponSkin, List.of("value1","value3"), 110, 190, 100));
        expected.add(createItem("test33", ItemType.WeaponSkin, List.of("value1","value3"), 110, 190, 100));

        Collection<Item> allItems = new ArrayList<>(expected);
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.CharacterHeadgear, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of(), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 90, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 210, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 40));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 160));
        allItems.add(createItem("test5", ItemType.WeaponSkin, List.of("value1"), 110, 190, 150));
        allItems.add(createItem("test6", ItemType.WeaponSkin, List.of("value1"), 110, 190, 150));
        allItems.add(createItem("test9", ItemType.Charm, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test3", ItemType.WeaponSkin, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test7", ItemType.WeaponSkin, List.of("value1"), 110, 190, 150));

        ItemFilter allowFilter1 = new ItemFilter();
        allowFilter1.setFilterType(FilterType.ALLOW);
        allowFilter1.setItemNamePatterns(List.of("test1", "test2"));
        allowFilter1.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        allowFilter1.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        allowFilter1.setMinPrice(100);
        allowFilter1.setMaxPrice(200);
        allowFilter1.setMinLastSoldPrice(50);
        allowFilter1.setMaxLastSoldPrice(150);

        ItemFilter allowFilter2 = new ItemFilter();
        allowFilter2.setFilterType(FilterType.ALLOW);
        allowFilter2.setItemNamePatterns(List.of("test3", "test4"));
        allowFilter2.setItemTypes(List.of());
        allowFilter2.setTags(List.of(new Tag("value3", "name3", TagGroup.Rarity), new Tag("value4", "name4", TagGroup.Season)));
        allowFilter2.setMinPrice(0);
        allowFilter2.setMaxPrice(999);
        allowFilter2.setMinLastSoldPrice(0);
        allowFilter2.setMaxLastSoldPrice(999);

        ItemFilter allowFilter3 = new ItemFilter();
        allowFilter3.setFilterType(FilterType.ALLOW);
        allowFilter3.setItemNamePatterns(List.of("test9"));
        allowFilter3.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        allowFilter3.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        allowFilter3.setMinPrice(100);
        allowFilter3.setMaxPrice(200);
        allowFilter3.setMinLastSoldPrice(50);
        allowFilter3.setMaxLastSoldPrice(150);

        ItemFilter denyFilter1 = new ItemFilter();
        denyFilter1.setFilterType(FilterType.DENY);
        denyFilter1.setItemNamePatterns(List.of("test1", "test2"));
        denyFilter1.setItemTypes(List.of(ItemType.Charm));
        denyFilter1.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        denyFilter1.setMinPrice(100);
        denyFilter1.setMaxPrice(200);
        denyFilter1.setMinLastSoldPrice(50);
        denyFilter1.setMaxLastSoldPrice(150);

        ItemFilter denyFilter2 = new ItemFilter();
        denyFilter2.setFilterType(FilterType.DENY);
        denyFilter2.setItemNamePatterns(List.of("test5", "test6"));
        denyFilter2.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        denyFilter2.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        denyFilter2.setMinPrice(100);
        denyFilter2.setMaxPrice(200);
        denyFilter2.setMinLastSoldPrice(50);
        denyFilter2.setMaxLastSoldPrice(150);

        ItemFilter denyFilter3 = new ItemFilter();
        denyFilter3.setFilterType(FilterType.DENY);
        denyFilter3.setItemNamePatterns(List.of("test9"));
        denyFilter3.setItemTypes(null);
        denyFilter3.setTags(null);
        denyFilter3.setMinPrice(0);
        denyFilter3.setMaxPrice(9999);
        denyFilter3.setMinLastSoldPrice(0);
        denyFilter3.setMaxLastSoldPrice(9999);

        Collection<Item> result = ItemFilter.filterItems(allItems,List.of(allowFilter1,allowFilter2,denyFilter1,denyFilter2));

        for(Item item: result){
            System.out.println(item);
        }

        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }

    @Test
    public void filterItems_should_return_list_by_filter_parameters() {
        Collection<Item> expected = new ArrayList<>();
        expected.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 50));
        expected.add(createItem("test1111", ItemType.WeaponSkin, List.of("value1"), 110, 190, 150));
        expected.add(createItem("test2", ItemType.WeaponSkin, List.of("value1","value3"), 110, 190, 100));

        Collection<Item> allItems = new ArrayList<>(expected);
        allItems.add(createItem("test3", ItemType.WeaponSkin, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.CharacterHeadgear, List.of("value1", "value2"), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of(), 100, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 90, 200, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 210, 50));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 40));
        allItems.add(createItem("test1", ItemType.Charm, List.of("value1", "value2"), 100, 200, 160));

        ItemFilter filter = new ItemFilter();
        filter.setItemNamePatterns(List.of("test1", "test2"));
        filter.setItemTypes(List.of(ItemType.Charm, ItemType.WeaponSkin));
        filter.setTags(List.of(new Tag("value1", "name1", TagGroup.Rarity), new Tag("value2", "name2", TagGroup.Season)));
        filter.setMinPrice(100);
        filter.setMaxPrice(200);
        filter.setMinLastSoldPrice(50);
        filter.setMaxLastSoldPrice(150);

        Collection<Item> result = filter.filterItems(allItems);

        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }

    private Item createItem(String name, ItemType type, List<String> tagValues, int minSellPrice, int maxBuyPrice, int lastSoldPrice) {
        Item item = new Item();
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
        filter.setMinPrice(null);
        filter.setMaxPrice(null);
        filter.setMinLastSoldPrice(null);
        filter.setMaxLastSoldPrice(null);
        filter.setIsOwned(null);
        filter.setFilterType(null);
        filter.setName(null);

        assertDoesNotThrow(filter::toString);
    }
}