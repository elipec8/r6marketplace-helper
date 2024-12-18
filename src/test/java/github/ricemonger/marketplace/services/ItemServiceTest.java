package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.ItemFilter;
import github.ricemonger.utils.DTOs.items.*;
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
    private ItemDatabaseService itemDatabaseService;
    @MockBean
    private ItemSaleDatabaseService itemSaleDatabaseService;
    @MockBean
    private TagService tagService;
    @MockBean
    private CommonValuesService commonValuesService;
    @MockBean
    private ItemSaleUbiStatsService itemSaleUbiStatsService;

    @Test
    public void saveAllItemLastSales_should_handle_to_service() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setItemId("1");
        item.setTags(new ArrayList<>());

        List<ItemEntityDTO> items = List.of(item);

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

        ItemMainFieldsI item1 = new ItemEntityDTO();
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

        ItemMainFieldsI item2 = new ItemEntityDTO();
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

        ItemMainFieldsI item3 = new ItemEntityDTO();
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

        ItemMainFieldsI item4 = new ItemEntityDTO();
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

        ItemMainFieldsI item5 = new ItemEntityDTO();
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

        ItemEntityDTO existingItem1 = new ItemEntityDTO();
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
        existingItem1.setDayAveragePrice(6);
        existingItem1.setDayMedianPrice(7);
        existingItem1.setDayMaxPrice(8);
        existingItem1.setDayMinPrice(9);
        existingItem1.setDaySales(10);
        existingItem1.setPriceToSellIn1Hour(11);
        existingItem1.setPriceToSellIn6Hours(12);
        existingItem1.setPriceToSellIn24Hours(13);
        existingItem1.setPriceToSellIn168Hours(14);
        existingItem1.setPriceToBuyIn1Hour(15);
        existingItem1.setPriceToBuyIn6Hours(16);
        existingItem1.setPriceToBuyIn24Hours(17);
        existingItem1.setPriceToBuyIn168Hours(18);

        ItemEntityDTO existingItem2 = new ItemEntityDTO();
        existingItem2.setItemId("itemId6");
        existingItem2.setName("name2");
        existingItem2.setTags(List.of());

        List<ItemEntityDTO> existingItems = List.of(existingItem1, existingItem2);

        when(itemDatabaseService.findAll()).thenReturn(existingItems);

        ItemEntityDTO updatedItem1 = new ItemEntityDTO();
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
        updatedItem1.setDayAveragePrice(6);
        updatedItem1.setDayMedianPrice(7);
        updatedItem1.setDayMaxPrice(8);
        updatedItem1.setDayMinPrice(9);
        updatedItem1.setDaySales(10);
        updatedItem1.setPriceToSellIn1Hour(11);
        updatedItem1.setPriceToSellIn6Hours(12);
        updatedItem1.setPriceToSellIn24Hours(13);
        updatedItem1.setPriceToSellIn168Hours(14);
        updatedItem1.setPriceToBuyIn1Hour(15);
        updatedItem1.setPriceToBuyIn6Hours(16);
        updatedItem1.setPriceToBuyIn24Hours(17);
        updatedItem1.setPriceToBuyIn168Hours(18);

        ItemEntityDTO updatedItem2 = new ItemEntityDTO(item2);
        updatedItem2.setRarity(ItemRarity.LEGENDARY);

        ItemEntityDTO updatedItem3 = new ItemEntityDTO(item3);
        updatedItem3.setRarity(ItemRarity.EPIC);

        ItemEntityDTO updatedItem4 = new ItemEntityDTO(item4);
        updatedItem4.setRarity(ItemRarity.RARE);

        ItemEntityDTO updatedItem5 = new ItemEntityDTO(item5);
        updatedItem5.setRarity(ItemRarity.UNCOMMON);

        List<ItemEntityDTO> updatedItems = new ArrayList<>();
        updatedItems.add(updatedItem1);
        updatedItems.add(updatedItem2);
        updatedItems.add(updatedItem3);
        updatedItems.add(updatedItem4);
        updatedItems.add(updatedItem5);

        itemService.saveAllItemsMainFields(itemsMainFields);

        verify(itemDatabaseService).saveAll(argThat(arg -> arg.size() == updatedItems.size() &&
                                                           updatedItems.stream().allMatch(updatedItem ->
                                                                   arg.stream().anyMatch(actualItem ->
                                                                           actualItem.isFullyEqualTo(updatedItem)
                                                                   )
                                                           )));
    }

    @Test
    public void recalculateAndSaveAllItemsHistoryFields_should_recalculate_only_history_fields_and_save_all_items() {
        ItemEntityDTO existingItemToRecalculate1 = new ItemEntityDTO();
        existingItemToRecalculate1.setItemId("itemId1");
        existingItemToRecalculate1.setAssetUrl("assetUrl1");
        existingItemToRecalculate1.setName("name1");
        existingItemToRecalculate1.setTags(List.of());
        existingItemToRecalculate1.setRarity(ItemRarity.RARE);
        existingItemToRecalculate1.setType(ItemType.WeaponSkin);
        existingItemToRecalculate1.setMaxBuyPrice(1);
        existingItemToRecalculate1.setBuyOrdersCount(2);
        existingItemToRecalculate1.setMinSellPrice(3);
        existingItemToRecalculate1.setSellOrdersCount(4);
        existingItemToRecalculate1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        existingItemToRecalculate1.setLastSoldPrice(5);

        ItemEntityDTO existingItemToRecalculate2 = new ItemEntityDTO();
        existingItemToRecalculate2.setItemId("itemId2");
        existingItemToRecalculate2.setAssetUrl("assetUrl2");
        existingItemToRecalculate2.setName("name2");
        existingItemToRecalculate2.setTags(List.of());
        existingItemToRecalculate2.setRarity(ItemRarity.EPIC);
        existingItemToRecalculate2.setType(ItemType.CharacterHeadgear);
        existingItemToRecalculate2.setMaxBuyPrice(2);
        existingItemToRecalculate2.setBuyOrdersCount(3);
        existingItemToRecalculate2.setMinSellPrice(4);
        existingItemToRecalculate2.setSellOrdersCount(5);
        existingItemToRecalculate2.setLastSoldAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        existingItemToRecalculate2.setLastSoldPrice(6);

        ItemEntityDTO existingItemNoSales = new ItemEntityDTO();
        existingItemNoSales.setItemId("itemId3");
        existingItemNoSales.setAssetUrl("assetUrl3");
        existingItemNoSales.setName("name3");
        existingItemNoSales.setTags(List.of());
        existingItemNoSales.setRarity(ItemRarity.UNKNOWN);
        existingItemNoSales.setType(ItemType.Unknown);
        existingItemNoSales.setMaxBuyPrice(1);
        existingItemNoSales.setBuyOrdersCount(2);
        existingItemNoSales.setMinSellPrice(3);
        existingItemNoSales.setSellOrdersCount(4);
        existingItemNoSales.setLastSoldAt(LocalDateTime.of(2023, 1, 1, 0, 0));
        existingItemNoSales.setLastSoldPrice(5);
        existingItemNoSales.setMonthAveragePrice(1);
        existingItemNoSales.setMonthMedianPrice(2);
        existingItemNoSales.setMonthMaxPrice(3);
        existingItemNoSales.setMonthMinPrice(4);
        existingItemNoSales.setMonthSalesPerDay(5);
        existingItemNoSales.setDayAveragePrice(6);
        existingItemNoSales.setDayMedianPrice(7);
        existingItemNoSales.setDayMaxPrice(8);
        existingItemNoSales.setDayMinPrice(9);
        existingItemNoSales.setDaySales(10);
        existingItemNoSales.setPriceToSellIn1Hour(11);
        existingItemNoSales.setPriceToSellIn6Hours(12);
        existingItemNoSales.setPriceToSellIn24Hours(13);
        existingItemNoSales.setPriceToSellIn168Hours(14);
        existingItemNoSales.setPriceToBuyIn1Hour(15);
        existingItemNoSales.setPriceToBuyIn6Hours(16);
        existingItemNoSales.setPriceToBuyIn24Hours(17);
        existingItemNoSales.setPriceToBuyIn168Hours(18);

        List<ItemEntityDTO> existingItems = List.of(existingItemToRecalculate1, existingItemToRecalculate2, existingItemNoSales);

        when(itemDatabaseService.findAll()).thenReturn(existingItems);

        ItemSale item1TodaySale1 = new ItemSale("itemId1", LocalDateTime.now().withMinute(1).withNano(0), 100);
        ItemSale item1TodaySale2 = new ItemSale("itemId1", LocalDateTime.now().withMinute(2).withNano(0), 200);
        ItemSale item1TodaySale3 = new ItemSale("itemId1", LocalDateTime.now().withMinute(3).withNano(0), 600);

        ItemSale item1Day7Sale1 = new ItemSale("itemId1", LocalDateTime.now().minusDays(7).withMinute(1).withNano(0), 200);
        ItemSale item1Day7Sale2 = new ItemSale("itemId1", LocalDateTime.now().minusDays(7).withMinute(2).withNano(0), 400);
        ItemSale item1Day7Sale3 = new ItemSale("itemId1", LocalDateTime.now().minusDays(7).withMinute(3).withNano(0), 1200);

        ItemSale item1Day30Sale1 = new ItemSale("itemId1", LocalDateTime.now().minusDays(30).withMinute(1).withNano(0), 400);
        ItemSale item1Day30Sale2 = new ItemSale("itemId1", LocalDateTime.now().minusDays(30).withMinute(2).withNano(0), 800);
        ItemSale item1Day30Sale3 = new ItemSale("itemId1", LocalDateTime.now().minusDays(30).withMinute(3).withNano(0), 2400);

        ItemSale item1Day31Sale1 = new ItemSale("itemId1", LocalDateTime.now().minusDays(31).withMinute(1).withNano(0), 800);
        ItemSale item1Day31Sale2 = new ItemSale("itemId1", LocalDateTime.now().minusDays(31).withMinute(2).withNano(0), 1600);
        ItemSale item1Day31Sale3 = new ItemSale("itemId1", LocalDateTime.now().minusDays(31).withMinute(3).withNano(0), 4800);

        ItemSale item2Day7Sale1 = new ItemSale("itemId2", LocalDateTime.now().minusDays(7).withMinute(1).withNano(0), 200);
        ItemSale item2Day7Sale2 = new ItemSale("itemId2", LocalDateTime.now().minusDays(7).withMinute(2).withNano(0), 800);
        ItemSale item2Day7Sale3 = new ItemSale("itemId2", LocalDateTime.now().minusDays(7).withMinute(3).withNano(0), 5000);

        List<ItemSale> existingItemSales = List.of(item1TodaySale1, item1TodaySale2, item1TodaySale3, item1Day7Sale1, item1Day7Sale2, item1Day7Sale3, item1Day30Sale1, item1Day30Sale2, item1Day30Sale3, item1Day31Sale1, item1Day31Sale2, item1Day31Sale3, item2Day7Sale1, item2Day7Sale2, item2Day7Sale3);
        when(itemSaleDatabaseService.findAllForLastMonth()).thenReturn(existingItemSales);

        //  4-200, 1-1200
        ItemDaySalesUbiStatsEntityDTO item1Day7Stats = new ItemDaySalesUbiStatsEntityDTO("itemId1", LocalDate.now().minusDays(7), 200, 400, 1200, 5);

        ItemDaySalesUbiStatsEntityDTO item1Day30Stats = new ItemDaySalesUbiStatsEntityDTO("itemId1", LocalDate.now().minusDays(30), 400, 2000, 3200, 4);

        ItemDaySalesUbiStatsEntityDTO item1Day32Stats = new ItemDaySalesUbiStatsEntityDTO("itemId1", LocalDate.now().minusDays(32), 800, 1600, 48000, 3);

        //1 - 100, 26-300,  1 - 500
        ItemDaySalesUbiStatsEntityDTO item2Day15Stats = new ItemDaySalesUbiStatsEntityDTO("itemId2", LocalDate.now().minusDays(15), 100, 300, 500, 28);

        List<ItemDaySalesUbiStatsEntityDTO> existingItemDaySalesUbiStatEntityDTOS = List.of(item1Day7Stats, item1Day30Stats, item1Day32Stats, item2Day15Stats);
        when(itemSaleUbiStatsService.findAllForLastMonth()).thenReturn(existingItemDaySalesUbiStatEntityDTOS);

        ItemEntityDTO expectedRecalculatedItem1 = new ItemEntityDTO();
        expectedRecalculatedItem1.setItemId("itemId1");
        expectedRecalculatedItem1.setAssetUrl("assetUrl1");
        expectedRecalculatedItem1.setName("name1");
        expectedRecalculatedItem1.setTags(List.of());
        expectedRecalculatedItem1.setRarity(ItemRarity.RARE);
        expectedRecalculatedItem1.setType(ItemType.WeaponSkin);
        expectedRecalculatedItem1.setMaxBuyPrice(1);
        expectedRecalculatedItem1.setBuyOrdersCount(2);
        expectedRecalculatedItem1.setMinSellPrice(3);
        expectedRecalculatedItem1.setSellOrdersCount(4);
        expectedRecalculatedItem1.setLastSoldAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        expectedRecalculatedItem1.setLastSoldPrice(5);
        expectedRecalculatedItem1.setMonthAveragePrice(590);
        expectedRecalculatedItem1.setMonthMedianPrice(200);
        expectedRecalculatedItem1.setMonthMaxPrice(2400);
        expectedRecalculatedItem1.setMonthMinPrice(100);
        expectedRecalculatedItem1.setMonthSalesPerDay(0);
        expectedRecalculatedItem1.setDayAveragePrice(300);
        expectedRecalculatedItem1.setDayMedianPrice(200);
        expectedRecalculatedItem1.setDayMaxPrice(600);
        expectedRecalculatedItem1.setDayMinPrice(100);
        expectedRecalculatedItem1.setDaySales(3);
        expectedRecalculatedItem1.setPriceToSellIn1Hour(0);
        expectedRecalculatedItem1.setPriceToSellIn6Hours(0);
        expectedRecalculatedItem1.setPriceToSellIn24Hours(0);
        expectedRecalculatedItem1.setPriceToSellIn168Hours(600);
        expectedRecalculatedItem1.setPriceToBuyIn1Hour(0);
        expectedRecalculatedItem1.setPriceToBuyIn6Hours(0);
        expectedRecalculatedItem1.setPriceToBuyIn24Hours(0);
        expectedRecalculatedItem1.setPriceToBuyIn168Hours(200);

        ItemEntityDTO expectedRecalculatedItem2 = new ItemEntityDTO();
        expectedRecalculatedItem2.setItemId("itemId2");
        expectedRecalculatedItem2.setAssetUrl("assetUrl2");
        expectedRecalculatedItem2.setName("name2");
        expectedRecalculatedItem2.setTags(List.of());
        expectedRecalculatedItem2.setRarity(ItemRarity.EPIC);
        expectedRecalculatedItem2.setType(ItemType.CharacterHeadgear);
        expectedRecalculatedItem2.setMaxBuyPrice(2);
        expectedRecalculatedItem2.setBuyOrdersCount(3);
        expectedRecalculatedItem2.setMinSellPrice(4);
        expectedRecalculatedItem2.setSellOrdersCount(5);
        expectedRecalculatedItem2.setLastSoldAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        expectedRecalculatedItem2.setLastSoldPrice(6);
        expectedRecalculatedItem2.setMonthAveragePrice(464);
        expectedRecalculatedItem2.setMonthMedianPrice(300);
        expectedRecalculatedItem2.setMonthMaxPrice(5000);
        expectedRecalculatedItem2.setMonthMinPrice(100);
        expectedRecalculatedItem2.setMonthSalesPerDay(1);
        expectedRecalculatedItem2.setDayAveragePrice(0);
        expectedRecalculatedItem2.setDayMedianPrice(0);
        expectedRecalculatedItem2.setDayMaxPrice(0);
        expectedRecalculatedItem2.setDayMinPrice(0);
        expectedRecalculatedItem2.setDaySales(0);
        expectedRecalculatedItem2.setPriceToSellIn1Hour(0);
        expectedRecalculatedItem2.setPriceToSellIn6Hours(0);
        expectedRecalculatedItem2.setPriceToSellIn24Hours(200);
        expectedRecalculatedItem2.setPriceToSellIn168Hours(300);
        expectedRecalculatedItem2.setPriceToBuyIn1Hour(0);
        expectedRecalculatedItem2.setPriceToBuyIn6Hours(0);
        expectedRecalculatedItem2.setPriceToBuyIn24Hours(800);
        expectedRecalculatedItem2.setPriceToBuyIn168Hours(300);

        ItemEntityDTO expectedItemNoSales = new ItemEntityDTO();
        expectedItemNoSales.setItemId("itemId3");
        expectedItemNoSales.setAssetUrl("assetUrl3");
        expectedItemNoSales.setName("name3");
        expectedItemNoSales.setTags(List.of());
        expectedItemNoSales.setRarity(ItemRarity.UNKNOWN);
        expectedItemNoSales.setType(ItemType.Unknown);
        expectedItemNoSales.setMaxBuyPrice(1);
        expectedItemNoSales.setBuyOrdersCount(2);
        expectedItemNoSales.setMinSellPrice(3);
        expectedItemNoSales.setSellOrdersCount(4);
        expectedItemNoSales.setLastSoldAt(LocalDateTime.of(2023, 1, 1, 0, 0));
        expectedItemNoSales.setLastSoldPrice(5);
        expectedItemNoSales.setMonthAveragePrice(0);
        expectedItemNoSales.setMonthMedianPrice(0);
        expectedItemNoSales.setMonthMaxPrice(0);
        expectedItemNoSales.setMonthMinPrice(0);
        expectedItemNoSales.setMonthSalesPerDay(0);
        expectedItemNoSales.setDayAveragePrice(0);
        expectedItemNoSales.setDayMedianPrice(0);
        expectedItemNoSales.setDayMaxPrice(0);
        expectedItemNoSales.setDayMinPrice(0);
        expectedItemNoSales.setDaySales(0);
        expectedItemNoSales.setPriceToSellIn1Hour(0);
        expectedItemNoSales.setPriceToSellIn6Hours(0);
        expectedItemNoSales.setPriceToSellIn24Hours(0);
        expectedItemNoSales.setPriceToSellIn168Hours(0);
        expectedItemNoSales.setPriceToBuyIn1Hour(0);
        expectedItemNoSales.setPriceToBuyIn6Hours(0);
        expectedItemNoSales.setPriceToBuyIn24Hours(0);
        expectedItemNoSales.setPriceToBuyIn168Hours(0);

        List<ItemEntityDTO> expectedResult = List.of(expectedRecalculatedItem1, expectedRecalculatedItem2, expectedItemNoSales);

        itemService.recalculateAndSaveAllItemsHistoryFields();

        verify(itemDatabaseService).saveAll(argThat(arg ->
                arg.size() == expectedResult.size() &&
                expectedResult.stream().allMatch(expectedItem ->
                        arg.stream().anyMatch(actualItem ->
                                actualItem.isFullyEqualTo(expectedItem)
                        )
                )
        ));
    }

    @Test
    public void getItemById_should_return_service_result() {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setItemId("1");

        when(itemDatabaseService.findById("1")).thenReturn(item);

        assertSame(item, itemService.getItemById("1"));
    }

    @Test
    public void getAllItemsByFilter_should_return_service_result_after_filtering() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("2");

        List<ItemEntityDTO> items = List.of(item1, item2);
        List<ItemEntityDTO> filteredItems = List.of(item1);

        ItemFilter itemFilter = new ItemFilter();
        List<ItemFilter> filters = List.of(itemFilter);

        when(itemDatabaseService.findAll()).thenReturn(items);

        MockedStatic<ItemFilter> itemFilterMock = mockStatic(ItemFilter.class);
        itemFilterMock.when(() -> ItemFilter.filterItems(same(items), same(filters))).thenReturn(filteredItems);

        assertSame(filteredItems, itemService.getAllItemsByFilters(filters));
    }

    @Test
    public void getAllItems_should_return_service_result() {
        ItemEntityDTO item1 = new ItemEntityDTO();
        item1.setItemId("1");
        ItemEntityDTO item2 = new ItemEntityDTO();
        item2.setItemId("2");

        List<ItemEntityDTO> items = List.of(item1, item2);

        when(itemDatabaseService.findAll()).thenReturn(items);

        assertSame(items, itemService.getAllItems());
    }
}