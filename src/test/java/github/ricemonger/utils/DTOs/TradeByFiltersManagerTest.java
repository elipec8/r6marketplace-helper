package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.enums.FilterType;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Must be tested separately, provides false negative results when whole test suite is run")
class TradeByFiltersManagerTest {

    @Test
    public void getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority_should_return_empty_list_if_tradeByFiltersManagers_or_existing_items_is_null_or_empty() {
        ItemFilter itemFilter = new ItemFilter();
        itemFilter.setName("allowItem1");
        itemFilter.setItemNamePatterns(List.of("item1"));
        itemFilter.setFilterType(FilterType.ALLOW);
        Item item = new Item("item1");
        item.setName("item1");
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setAppliedFilters(List.of(itemFilter));
        List<TradeByFiltersManager> tradeByFiltersManagers = List.of(tradeByFiltersManager);
        List<Item> existingItems = new ArrayList<>(List.of(item, new Item("item2")));

        assertTrue(TradeByFiltersManager.getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(null, existingItems).isEmpty());
        assertTrue(TradeByFiltersManager.getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(tradeByFiltersManagers, null).isEmpty());
        assertTrue(TradeByFiltersManager.getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(new ArrayList<>(), existingItems).isEmpty());
        assertTrue(TradeByFiltersManager.getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(tradeByFiltersManagers, new ArrayList<>()).isEmpty());
    }

    @Test
    public void getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority_should_return_filtered_list_of_items_by_priority() {
        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("allowAll1");
        itemFilter1.setFilterType(FilterType.ALLOW);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("denyName1");
        itemFilter2.setFilterType(FilterType.DENY);
        itemFilter2.setItemNamePatterns(List.of("name1"));

        List<ItemFilter> appliedFilters1 = List.of(itemFilter1, itemFilter2);

        Item item1 = new Item("item1");
        item1.setName("name1");
        Item item2 = new Item("item2");
        item2.setName("name2");
        Item item3 = new Item("item3");
        item3.setName("name3");

        TradeByFiltersManager tradeByFiltersManager1 = new TradeByFiltersManager();
        tradeByFiltersManager1.setAppliedFilters(appliedFilters1);
        tradeByFiltersManager1.setPriorityMultiplier(1);


        ItemFilter itemFilter3 = new ItemFilter();
        itemFilter1.setName("allowAll2");
        itemFilter1.setFilterType(FilterType.ALLOW);

        ItemFilter itemFilter4 = new ItemFilter();
        itemFilter2.setName("denyName2");
        itemFilter2.setFilterType(FilterType.DENY);
        itemFilter2.setItemNamePatterns(List.of("name2"));

        List<ItemFilter> appliedFilters2 = List.of(itemFilter3, itemFilter4);

        TradeByFiltersManager tradeByFiltersManager2 = new TradeByFiltersManager();
        tradeByFiltersManager2.setAppliedFilters(appliedFilters2);
        tradeByFiltersManager2.setPriorityMultiplier(2);

        List<Item> existingItems = List.of(item1, item2, item3);

        Set<PersonalItem> result =
                TradeByFiltersManager.getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(List.of(tradeByFiltersManager1, tradeByFiltersManager2),
                        existingItems);

        assertEquals(3, result.size());
        assertTrue(result.contains(new PersonalItem(item2, tradeByFiltersManager1)));
        assertTrue(result.contains(new PersonalItem(item1, tradeByFiltersManager2)));
        assertTrue(result.contains(new PersonalItem(item3, tradeByFiltersManager2)));
    }

    @Test
    public void toItemForCentralTradeManagerDTOs_should_return_empty_list_when_applied_filters_is_null() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setAppliedFilters(null);

        List<Item> existingItems = new ArrayList<>();
        existingItems.add(new Item("item1"));
        existingItems.add(new Item("item2"));

        Set<PersonalItem> result = tradeByFiltersManager.toItemForCentralTradeManagerDTOs(existingItems);

        assertTrue(result.isEmpty());
    }

    @Test
    public void toItemForCentralTradeManagerDTOs_should_return_empty_list_when_applied_filters_is_empty() {
        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setAppliedFilters(new ArrayList<>());

        List<Item> existingItems = new ArrayList<>();
        existingItems.add(new Item("item1"));
        existingItems.add(new Item("item2"));

        Set<PersonalItem> result = tradeByFiltersManager.toItemForCentralTradeManagerDTOs(existingItems);

        assertTrue(result.isEmpty());
    }

    @Test
    public void toItemForCentralTradeManagerDTOs_should_return_filtered_list_of_items() {
        ItemFilter itemFilter1 = new ItemFilter();
        itemFilter1.setName("allowAll");
        itemFilter1.setFilterType(FilterType.ALLOW);

        ItemFilter itemFilter2 = new ItemFilter();
        itemFilter2.setName("denyName1");
        itemFilter2.setFilterType(FilterType.DENY);
        itemFilter2.setItemNamePatterns(List.of("name1"));

        List<ItemFilter> appliedFilters = List.of(itemFilter1, itemFilter2);

        Item item1 = new Item("item1");
        item1.setName("name1");
        Item item2 = new Item("item2");
        item2.setName("name2");
        Item item3 = new Item("item3");
        item3.setName("name3");

        List<Item> existingItems = List.of(item1, item2, item3);


        TradeByFiltersManager tradeByFiltersManager = new TradeByFiltersManager();
        tradeByFiltersManager.setAppliedFilters(appliedFilters);
        tradeByFiltersManager.setPriorityMultiplier(1);

        Set<PersonalItem> result = tradeByFiltersManager.toItemForCentralTradeManagerDTOs(existingItems);

        assertEquals(2, result.size());
        assertTrue(result.stream().noneMatch(item -> item.getItemId().equals("item1")));
    }
}