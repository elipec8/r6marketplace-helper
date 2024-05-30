package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import github.ricemonger.utils.dtos.Trade;
import github.ricemonger.utils.enums.ItemType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ItemPostgresMapperTest {

    private final Item ITEM = new Item(
            "itemId",
            "assetUrl",
            "name",
            List.of("tag1", "tag2"),
            ItemType.Unknown,
            1,
            2,
            3,
            4,
            new Date(0),
            5,
            true,
            6,
            7,
            new ArrayList<Trade>());

    private final ItemEntity ITEM_ENTITY = new ItemEntity(
            "itemId",
            "assetUrl",
            "name",
            "tag1,tag2",
            ItemType.Unknown,
            1,
            2,
            3,
            4,
            new Date(0),
            5,
            6,
            7);

    private final ItemSale ITEM_SALE = new ItemSale(
            "itemId",
            new Date(0),
            5);

    private final ItemSaleEntity ITEM_SALE_ENTITY = new ItemSaleEntity(
            "itemId",
            new Date(0),
            5);

    private final ItemSaleHistory ITEM_SALE_HISTORY = new ItemSaleHistory(
            "itemId",
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            new Date(0),
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22);

    private final ItemSaleHistoryEntity ITEM_SALE_HISTORY_ENTITY = new ItemSaleHistoryEntity(
            "itemId",
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13,
            14,
            new Date(0),
            15,
            16,
            17,
            18,
            19,
            20,
            21,
            22);


    @SpyBean
    private ItemPostgresMapper itemPostgresMapper;

    @Test
    public void mapItemsEntities_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemEntities(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemsEntities_should_return_empty_list_if_empty() {
        assertEquals(0, itemPostgresMapper.mapItemEntities(new ArrayList<>()).size());
    }

    @Test
    public void mapItemsEntities_should_return_list_of_items_and_call_map_for_each_object() {
        assertTrue(itemsEntitiesAreEqual(ITEM_ENTITY, new ArrayList<>(itemPostgresMapper.mapItemEntities(List.of(ITEM))).get(0)));

        verify(itemPostgresMapper).mapItemEntity(ITEM);
    }

    @Test
    public void mapItemEntity_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemEntity(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemEntity_should_not_throw_if_item_is_empty() {
        Executable executable = () -> itemPostgresMapper.mapItemEntity(new Item());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapItemEntity_should_return_item() {
        assertTrue(itemsEntitiesAreEqual(ITEM_ENTITY, itemPostgresMapper.mapItemEntity(ITEM)));
    }

    @Test
    public void mapItems_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItems(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItems_should_return_empty_list_if_empty() {
        assertEquals(0, itemPostgresMapper.mapItems(new ArrayList<>()).size());
    }

    @Test
    public void mapItems_should_return_list_of_items_and_call_map_for_each_object() {
        assertTrue(itemsAreEqual(ITEM, new ArrayList<>(itemPostgresMapper.mapItems(List.of(ITEM_ENTITY))).get(0)));

        verify(itemPostgresMapper).mapItem(ITEM_ENTITY);
    }

    @Test
    public void mapItem_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItem(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItem_should_not_throw_if_entity_is_empty() {
        Executable executable = () -> itemPostgresMapper.mapItem(new ItemEntity());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapItem_should_return_item() {
        assertTrue(itemsAreEqual(ITEM, itemPostgresMapper.mapItem(ITEM_ENTITY)));
    }

    @Test
    public void mapItemSaleEntities_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleEntities(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSaleEntities_should_return_empty_list_if_empty() {
        assertEquals(0, itemPostgresMapper.mapItemSaleEntities(new ArrayList<>()).size());
    }

    @Test
    public void mapItemSaleEntities_should_return_list_of_items_and_call_map_for_each_object() {
        assertTrue(itemSalesEntitiesAreEqual(ITEM_SALE_ENTITY, new ArrayList<>(itemPostgresMapper.mapItemSaleEntities(List.of(ITEM_SALE))).get(0)));

        verify(itemPostgresMapper).mapItemSaleEntity(ITEM_SALE);
    }

    @Test
    public void mapItemSaleEntity_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleEntity(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSaleEntity_should_not_throw_if_sale_is_empty() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleEntity(new ItemSale());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapItemSaleEntity_should_return_item_sale() {
        assertTrue(itemSalesEntitiesAreEqual(ITEM_SALE_ENTITY, itemPostgresMapper.mapItemSaleEntity(ITEM_SALE)));
    }

    @Test
    public void mapItemSales_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSales(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSales_should_return_empty_list_if_empty() {
        assertEquals(0, itemPostgresMapper.mapItemSales(new ArrayList<>()).size());
    }

    @Test
    public void mapItemSales_should_return_list_of_items_and_call_map_for_each_object() {
        assertTrue(itemSalesAreEqual(ITEM_SALE, new ArrayList<>(itemPostgresMapper.mapItemSales(List.of(ITEM_SALE_ENTITY))).get(0)));

        verify(itemPostgresMapper).mapItemSale(ITEM_SALE_ENTITY);
    }

    @Test
    public void mapItemSale_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSale(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSale_should_not_throw_if_entity_is_empty() {
        Executable executable = () -> itemPostgresMapper.mapItemSale(new ItemSaleEntity());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapItemSale_should_return_item_sale() {
        assertEquals(ITEM_SALE, itemPostgresMapper.mapItemSale(ITEM_SALE_ENTITY));
    }

    @Test
    public void mapItemSaleHistoryEntities_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleHistoryEntities(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSaleHistoryEntities_should_return_empty_list_if_empty() {
        assertEquals(0, itemPostgresMapper.mapItemSaleHistoryEntities(new ArrayList<>()).size());
    }

    @Test
    public void mapItemSaleHistoryEntities_should_return_list_of_items_and_call_map_for_each_object() {
        assertTrue(itemSaleHistoryEntitiesAreEqual(ITEM_SALE_HISTORY_ENTITY,
                new ArrayList<>(itemPostgresMapper.mapItemSaleHistoryEntities(List.of(ITEM_SALE_HISTORY))).get(0)));

        verify(itemPostgresMapper).mapItemSaleHistoryEntity(ITEM_SALE_HISTORY);
    }

    @Test
    public void mapItemSaleHistoryEntity_should_throw_if_null() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleHistoryEntity(null);

        assertThrows(NullPointerException.class, executable);
    }

    @Test
    public void mapItemSaleHistoryEntity_should_not_throw_if_history_is_empty() {
        Executable executable = () -> itemPostgresMapper.mapItemSaleHistoryEntity(new ItemSaleHistory());

        assertDoesNotThrow(executable);
    }

    @Test
    public void mapItemSaleHistoryEntity_should_return_item_sale_history() {
        assertTrue(itemSaleHistoryEntitiesAreEqual(ITEM_SALE_HISTORY_ENTITY, itemPostgresMapper.mapItemSaleHistoryEntity(ITEM_SALE_HISTORY)));
    }

    private boolean itemSaleHistoriesAreEqual(ItemSaleHistory history1, ItemSaleHistory history2) {
        return history1.getItemId().equals(history2.getItemId()) &&
               history1.getMonthAveragePrice() == history2.getMonthAveragePrice() &&
               history1.getMonthMedianPrice() == history2.getMonthMedianPrice() &&
               history1.getMonthMinPrice() == history2.getMonthMinPrice() &&
               history1.getMonthMaxPrice() == history2.getMonthMaxPrice() &&
               history1.getMonthSalesPerDay() == history2.getMonthSalesPerDay() &&
               history1.getDayAveragePrice() == history2.getDayAveragePrice() &&
               history1.getDayMedianPrice() == history2.getDayMedianPrice() &&
               history1.getDayMinPrice() == history2.getDayMinPrice() &&
               history1.getDayMaxPrice() == history2.getDayMaxPrice() &&
               history1.getDaySales() == history2.getDaySales() &&
               history1.getExpectedProfitByCurrentPrices() == history2.getExpectedProfitByCurrentPrices() &&
               history1.getExpectedProfitPercentByCurrentPrices() == history2.getExpectedProfitPercentByCurrentPrices() &&
               history1.getHoursToSellFor120() == history2.getHoursToSellFor120() &&
               history1.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() == history2.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() &&
               history1.getLastDateCurrentPriceOrLastSoldWasHigherThan120().equals(history2.getLastDateCurrentPriceOrLastSoldWasHigherThan120()) &&
               history1.getPriceToSellIn1Hour() == history2.getPriceToSellIn1Hour() &&
               history1.getPriceToSellIn6Hours() == history2.getPriceToSellIn6Hours() &&
               history1.getPriceToSellIn24Hours() == history2.getPriceToSellIn24Hours() &&
               history1.getPriceToSellIn168Hours() == history2.getPriceToSellIn168Hours() &&
               history1.getPriceToBuyIn1Hour() == history2.getPriceToBuyIn1Hour() &&
               history1.getPriceToBuyIn6Hours() == history2.getPriceToBuyIn6Hours() &&
               history1.getPriceToBuyIn24Hours() == history2.getPriceToBuyIn24Hours() &&
               history1.getPriceToBuyIn168Hours() == history2.getPriceToBuyIn168Hours();
    }

    private boolean itemSaleHistoryEntitiesAreEqual(ItemSaleHistoryEntity entity1, ItemSaleHistoryEntity entity2) {
        return entity1.getItemId().equals(entity2.getItemId()) &&
               entity1.getMonthAveragePrice() == entity2.getMonthAveragePrice() &&
               entity1.getMonthMedianPrice() == entity2.getMonthMedianPrice() &&
               entity1.getMonthMinPrice() == entity2.getMonthMinPrice() &&
               entity1.getMonthMaxPrice() == entity2.getMonthMaxPrice() &&
               entity1.getMonthSalesPerDay() == entity2.getMonthSalesPerDay() &&
               entity1.getDayAveragePrice() == entity2.getDayAveragePrice() &&
               entity1.getDayMedianPrice() == entity2.getDayMedianPrice() &&
               entity1.getDayMinPrice() == entity2.getDayMinPrice() &&
               entity1.getDayMaxPrice() == entity2.getDayMaxPrice() &&
               entity1.getDaySales() == entity2.getDaySales() &&
               entity1.getExpectedProfitByCurrentPrices() == entity2.getExpectedProfitByCurrentPrices() &&
               entity1.getExpectedProfitPercentByCurrentPrices() == entity2.getExpectedProfitPercentByCurrentPrices() &&
               entity1.getHoursToSellFor120() == entity2.getHoursToSellFor120() &&
               entity1.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() == entity2.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120() &&
               entity1.getLastDateCurrentPriceOrLastSoldWasHigherThan120().equals(entity2.getLastDateCurrentPriceOrLastSoldWasHigherThan120()) &&
               entity1.getPriceToSellIn1Hour() == entity2.getPriceToSellIn1Hour() &&
               entity1.getPriceToSellIn6Hours() == entity2.getPriceToSellIn6Hours() &&
               entity1.getPriceToSellIn24Hours() == entity2.getPriceToSellIn24Hours() &&
               entity1.getPriceToSellIn168Hours() == entity2.getPriceToSellIn168Hours() &&
               entity1.getPriceToBuyIn1Hour() == entity2.getPriceToBuyIn1Hour() &&
               entity1.getPriceToBuyIn6Hours() == entity2.getPriceToBuyIn6Hours() &&
               entity1.getPriceToBuyIn24Hours() == entity2.getPriceToBuyIn24Hours() &&
               entity1.getPriceToBuyIn168Hours() == entity2.getPriceToBuyIn168Hours();
    }

    private boolean itemSalesEntitiesAreEqual(ItemSaleEntity itemSaleEntity, ItemSaleEntity itemSaleEntity1) {
        return itemSaleEntity.getItemId().equals(itemSaleEntity1.getItemId()) &&
               itemSaleEntity.getSoldAt().equals(itemSaleEntity1.getSoldAt()) &&
               itemSaleEntity.getPrice() == itemSaleEntity1.getPrice();
    }

    private boolean itemSalesAreEqual(ItemSale item1, ItemSale item2) {
        return item1.getItemId().equals(item2.getItemId()) &&
               item1.getSoldAt().equals(item2.getSoldAt()) &&
               item1.getPrice() == item2.getPrice();
    }

    private boolean itemsAreEqual(Item item1, Item item2) {
        return item1.getItemId().equals(item2.getItemId()) &&
               item1.getAssetUrl().equals(item2.getAssetUrl()) &&
               item1.getName().equals(item2.getName()) &&
               item1.getTags().equals(item2.getTags()) &&
               item1.getType().equals(item2.getType()) &&
               item1.getMaxBuyPrice() == item2.getMaxBuyPrice() &&
               item1.getBuyOrdersCount() == item2.getBuyOrdersCount() &&
               item1.getMinSellPrice() == item2.getMinSellPrice() &&
               item1.getSellOrdersCount() == item2.getSellOrdersCount() &&
               item1.getLastSoldAt().equals(item2.getLastSoldAt()) &&
               item1.getLastSoldPrice() == item2.getLastSoldPrice() &&
               item1.getLimitMinPrice() == item2.getLimitMinPrice() &&
               item1.getLimitMaxPrice() == item2.getLimitMaxPrice();
    }

    private boolean itemsEntitiesAreEqual(ItemEntity entity1, ItemEntity entity2) {
        return entity1.getItemId().equals(entity2.getItemId()) &&
               entity1.getAssetUrl().equals(entity2.getAssetUrl()) &&
               entity1.getName().equals(entity2.getName()) &&
               entity1.getTags().equals(entity2.getTags()) &&
               entity1.getType().equals(entity2.getType()) &&
               entity1.getMaxBuyPrice() == entity2.getMaxBuyPrice() &&
               entity1.getBuyOrdersCount() == entity2.getBuyOrdersCount() &&
               entity1.getMinSellPrice() == entity2.getMinSellPrice() &&
               entity1.getSellOrdersCount() == entity2.getSellOrdersCount() &&
               entity1.getLastSoldAt().equals(entity2.getLastSoldAt()) &&
               entity1.getLastSoldPrice() == entity2.getLastSoldPrice() &&
               entity1.getLimitMinPrice() == entity2.getLimitMinPrice() &&
               entity1.getLimitMaxPrice() == entity2.getLimitMaxPrice();
    }
}