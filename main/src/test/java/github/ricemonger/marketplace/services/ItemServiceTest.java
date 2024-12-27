package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.common.*;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TagGroup;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@SpringBootTest
class ItemServiceTest {
    @Autowired
    private ItemService itemService;
    @MockBean
    private TagService tagService;
    @MockBean
    private ItemDatabaseService itemDatabaseService;
    @MockBean
    private ItemSaleDatabaseService itemSaleDatabaseService;
    @MockBean
    private ItemSaleUbiStatsService itemSaleUbiStatsService;
    @MockBean
    private PotentialTradeStatsService potentialTradeStatsService;

    @Test
    public void saveAllItemLastSales_should_handle_to_service() {
        Item item = new Item();
        item.setItemId("1");
        item.setTags(new ArrayList<>());

        List<Item> items = List.of(item);

        itemService.saveAllItemLastSales(items);

        verify(itemSaleDatabaseService).saveAll(same(items));
    }

    @Test
    public void saveAllItemSaleUbiStats_should_handle_to_service() {
        GroupedItemDaySalesUbiStats ubiStats = new GroupedItemDaySalesUbiStats();
        ubiStats.setItemId("1");

        List<GroupedItemDaySalesUbiStats> groupedUbiStats = List.of(ubiStats);

        itemService.saveAllItemSaleUbiStats(groupedUbiStats);

        verify(itemSaleUbiStatsService).saveAll(same(groupedUbiStats));
    }

    @Test
    public void saveAllItemsMainFields_should_get_history_fields_from_db_and_update_main_fields_with_rarity() {
        Tag tagUncommon = new Tag("uncommon_value", "UNCOMMON", TagGroup.Season);
        Tag tagRare = new Tag("rare_value", "RARE", TagGroup.Rarity);
        Tag tagEpic = new Tag("epic_value", "EPIC", TagGroup.Operator);
        Tag tagLegendary = new Tag("legendary_value", "LEGENDARY", TagGroup.Weapon);
        Tag tagSeason = new Tag("y9s3", "Y9S3", TagGroup.Season);
        Tag tagOperator = new Tag("ace", "ACE", TagGroup.Operator);
        Tag tagWeapon = new Tag("ak12", "AK-12", TagGroup.Weapon);

        when(tagService.getTagsByNames(any())).thenReturn(List.of(tagUncommon, tagRare, tagEpic, tagLegendary));

        ItemMainFieldsI item1 = new Item();
        item1.setItemId("itemId1");
        item1.setAssetUrl("assetUrl1");
        item1.setName("name1");
        item1.setTags(List.of(tagOperator.getValue(), tagWeapon.getValue(), tagSeason.getValue(), tagUncommon.getValue()));
        item1.setRarity(ItemRarity.UNKNOWN);
        item1.setType(ItemType.Unknown);
        item1.setMaxBuyPrice(1);
        item1.setBuyOrdersCount(2);
        item1.setMinSellPrice(3);
        item1.setSellOrdersCount(4);
        item1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        item1.setLastSoldPrice(5);

        ItemMainFieldsI item2 = new Item();
        item2.setItemId("itemId2");
        item2.setAssetUrl("assetUrl2");
        item2.setName("name2");
        item2.setTags(List.of(tagOperator.getValue(), tagLegendary.getValue()));
        item2.setRarity(ItemRarity.UNCOMMON);
        item2.setType(ItemType.CharacterUniform);
        item2.setMaxBuyPrice(2);
        item2.setBuyOrdersCount(3);
        item2.setMinSellPrice(4);
        item2.setSellOrdersCount(5);
        item2.setLastSoldAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        item2.setLastSoldPrice(6);

        ItemMainFieldsI item3 = new Item();
        item3.setItemId("itemId3");
        item3.setAssetUrl("assetUrl3");
        item3.setName("name3");
        item3.setTags(List.of(tagWeapon.getValue(), tagSeason.getValue(), tagEpic.getValue()));
        item3.setRarity(ItemRarity.RARE);
        item3.setType(ItemType.CharacterHeadgear);
        item3.setMaxBuyPrice(3);
        item3.setBuyOrdersCount(4);
        item3.setMinSellPrice(5);
        item3.setSellOrdersCount(6);
        item3.setLastSoldAt(LocalDateTime.of(2023, 1, 1, 0, 0));
        item3.setLastSoldPrice(7);

        ItemMainFieldsI item4 = new Item();
        item4.setItemId("itemId4");
        item4.setAssetUrl("assetUrl4");
        item4.setName("name4");
        item4.setTags(List.of(tagWeapon.getValue(), tagRare.getValue()));
        item4.setRarity(ItemRarity.EPIC);
        item4.setType(ItemType.WeaponSkin);
        item4.setMaxBuyPrice(4);
        item4.setBuyOrdersCount(5);
        item4.setMinSellPrice(6);
        item4.setSellOrdersCount(7);
        item4.setLastSoldAt(LocalDateTime.of(2024, 1, 1, 0, 0));
        item4.setLastSoldPrice(8);

        ItemMainFieldsI item5 = new Item();
        item5.setItemId("itemId5");
        item5.setAssetUrl("assetUrl5");
        item5.setName("name5");
        item5.setTags(List.of(tagWeapon.getValue(), tagUncommon.getValue()));
        item5.setRarity(ItemRarity.LEGENDARY);
        item5.setType(ItemType.DroneSkin);
        item5.setMaxBuyPrice(5);
        item5.setBuyOrdersCount(6);
        item5.setMinSellPrice(7);
        item5.setSellOrdersCount(8);
        item5.setLastSoldAt(LocalDateTime.of(2025, 1, 1, 0, 0));
        item5.setLastSoldPrice(9);

        List<ItemMainFieldsI> itemsMainFields = List.of(item1, item2, item3, item4, item5);

        Item existingItem1 = new Item();
        existingItem1.setItemId("itemId1");
        existingItem1.setAssetUrl("assetUrlNotUpdated1");
        existingItem1.setName("nameNotUpdated1");
        existingItem1.setTags(List.of(tagOperator.getValue(), tagWeapon.getValue(), tagSeason.getValue(), tagLegendary.getValue()));
        existingItem1.setRarity(ItemRarity.RARE);
        existingItem1.setType(ItemType.Charm);
        existingItem1.setMaxBuyPrice(2);
        existingItem1.setBuyOrdersCount(3);
        existingItem1.setMinSellPrice(4);
        existingItem1.setSellOrdersCount(5);
        existingItem1.setLastSoldAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        existingItem1.setLastSoldPrice(6);
        existingItem1.setMonthAveragePrice(1);
        existingItem1.setMonthMedianPrice(2);
        existingItem1.setMonthMaxPrice(3);
        existingItem1.setMonthMinPrice(4);
        existingItem1.setMonthSalesPerDay(5);
        existingItem1.setMonthSales(6);
        existingItem1.setDayAveragePrice(7);
        existingItem1.setDayMedianPrice(8);
        existingItem1.setDayMaxPrice(9);
        existingItem1.setDayMinPrice(10);
        existingItem1.setDaySales(11);
        existingItem1.setPriorityToSellByMaxBuyPrice(12L);
        existingItem1.setPriorityToSellByNextFancySellPrice(13L);
        existingItem1.setPriorityToBuyByMinSellPrice(14L);
        existingItem1.setPriorityToBuyIn1Hour(15L);
        existingItem1.setPriorityToBuyIn6Hours(16L);
        existingItem1.setPriorityToBuyIn24Hours(17L);
        existingItem1.setPriorityToBuyIn168Hours(18L);
        existingItem1.setPriorityToBuyIn720Hours(19L);
        existingItem1.setPriceToBuyIn1Hour(20);
        existingItem1.setPriceToBuyIn6Hours(21);
        existingItem1.setPriceToBuyIn24Hours(22);
        existingItem1.setPriceToBuyIn168Hours(23);
        existingItem1.setPriceToBuyIn720Hours(24);

        Item existingItem2 = new Item();
        existingItem2.setItemId("itemId6");
        existingItem2.setName("name2");
        existingItem2.setTags(List.of());

        List<Item> existingItems = List.of(existingItem1, existingItem2);

        when(itemDatabaseService.findAll()).thenReturn(existingItems);

        Item updatedItem1 = new Item();
        updatedItem1.setItemId("itemId1");
        updatedItem1.setAssetUrl("assetUrl1");
        updatedItem1.setName("name1");
        updatedItem1.setTags(List.of(tagOperator.getValue(), tagWeapon.getValue(), tagSeason.getValue(), tagUncommon.getValue()));
        updatedItem1.setRarity(ItemRarity.UNCOMMON);
        updatedItem1.setType(ItemType.Unknown);
        updatedItem1.setMaxBuyPrice(1);
        updatedItem1.setBuyOrdersCount(2);
        updatedItem1.setMinSellPrice(3);
        updatedItem1.setSellOrdersCount(4);
        updatedItem1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        updatedItem1.setLastSoldPrice(5);
        updatedItem1.setMonthAveragePrice(1);
        updatedItem1.setMonthMedianPrice(2);
        updatedItem1.setMonthMaxPrice(3);
        updatedItem1.setMonthMinPrice(4);
        updatedItem1.setMonthSalesPerDay(5);
        updatedItem1.setMonthSales(6);
        updatedItem1.setDayAveragePrice(7);
        updatedItem1.setDayMedianPrice(8);
        updatedItem1.setDayMaxPrice(9);
        updatedItem1.setDayMinPrice(10);
        updatedItem1.setDaySales(11);
        updatedItem1.setPriorityToSellByMaxBuyPrice(12L);
        updatedItem1.setPriorityToSellByNextFancySellPrice(13L);
        updatedItem1.setPriorityToBuyByMinSellPrice(14L);
        updatedItem1.setPriorityToBuyIn1Hour(15L);
        updatedItem1.setPriorityToBuyIn6Hours(16L);
        updatedItem1.setPriorityToBuyIn24Hours(17L);
        updatedItem1.setPriorityToBuyIn168Hours(18L);
        updatedItem1.setPriorityToBuyIn720Hours(19L);
        updatedItem1.setPriceToBuyIn1Hour(20);
        updatedItem1.setPriceToBuyIn6Hours(21);
        updatedItem1.setPriceToBuyIn24Hours(22);
        updatedItem1.setPriceToBuyIn168Hours(23);
        updatedItem1.setPriceToBuyIn720Hours(24);

        when(potentialTradeStatsService.calculatePotentialSellTradeStatsByMaxBuyPrice(updatedItem1)).thenReturn(new PotentialTradeStats(0, 0, 12L));
        when(potentialTradeStatsService.calculatePotentialSellTradeStatsByNextFancySellPrice(updatedItem1)).thenReturn(new PotentialTradeStats(0, 0, 13L));
        when(potentialTradeStatsService.calculatePotentialBuyTradeStatsByMinSellPrice(updatedItem1)).thenReturn(new PotentialTradeStats(0, 0, 14L));

        Item updatedItem2 = new Item(item2);
        updatedItem2.setRarity(ItemRarity.LEGENDARY);

        Item updatedItem3 = new Item(item3);
        updatedItem3.setRarity(ItemRarity.EPIC);

        Item updatedItem4 = new Item(item4);
        updatedItem4.setRarity(ItemRarity.RARE);

        Item updatedItem5 = new Item(item5);
        updatedItem5.setRarity(ItemRarity.UNCOMMON);

        List<Item> updatedItems = new ArrayList<>();
        updatedItems.add(updatedItem1);
        updatedItems.add(updatedItem2);
        updatedItems.add(updatedItem3);
        updatedItems.add(updatedItem4);
        updatedItems.add(updatedItem5);

        itemService.saveAllItemsMainFields(itemsMainFields);

        System.out.println("Updated items: ");
        updatedItems.forEach(System.out::println);

        System.out.println("Saved items: ");

        verify(itemDatabaseService).saveAll(argThat(arg -> {
            arg.forEach(System.out::println);
            return arg.size() == updatedItems.size() &&
                   updatedItems.stream().allMatch(updatedItem ->
                           arg.stream().anyMatch(actualItem ->
                                   actualItem.isFullyEquals(updatedItem)
                           )
                   );
        }));
    }

    @Test
    public void getItemById_should_return_service_result() {
        Item item = new Item();
        item.setItemId("1");

        when(itemDatabaseService.findById("1")).thenReturn(item);

        assertSame(item, itemService.getItemById("1"));
    }

    @Test
    public void getAllItemsByFilter_should_return_service_result_after_filtering() {
        try (MockedStatic<ItemFilter> itemFilterMock = mockStatic(ItemFilter.class)) {
            Item item1 = new Item();
            item1.setItemId("1");
            Item item2 = new Item();
            item2.setItemId("2");

            List<Item> items = List.of(item1, item2);
            List<Item> filteredItems = List.of(item1);

            ItemFilter itemFilter = new ItemFilter();
            List<ItemFilter> filters = List.of(itemFilter);

            when(itemDatabaseService.findAll()).thenReturn(items);


            itemFilterMock.when(() -> ItemFilter.filterItems(same(items), same(filters))).thenReturn(filteredItems);

            assertSame(filteredItems, itemService.getAllItemsByFilters(filters));
        }
    }

    @Test
    public void getAllItems_should_return_service_result() {
        Item item1 = new Item();
        item1.setItemId("1");
        Item item2 = new Item();
        item2.setItemId("2");

        List<Item> items = List.of(item1, item2);

        when(itemDatabaseService.findAll()).thenReturn(items);

        assertSame(items, itemService.getAllItems());
    }
}