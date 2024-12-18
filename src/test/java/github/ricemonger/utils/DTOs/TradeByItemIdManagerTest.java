package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeOperationType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TradeByItemIdManagerTest {

    @Test
    public void getItemForCentralTradeManagerFromTradeByItemsIdManagersByPriority_should_return_empty_list_if_tradeByFiltersManagers_or_existing_items_is_null_or_empty() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("item1");
        tradeByItemIdManager.setTradeOperationType(TradeOperationType.BUY);
        tradeByItemIdManager.setEnabled(true);

        List<TradeByItemIdManager> tradeByItemIdManagers = List.of(tradeByItemIdManager);
        List<ItemEntityDTO> existingItems = new ArrayList<>(List.of(new ItemEntityDTO("item1"), new ItemEntityDTO("item2")));

        assertTrue(TradeByItemIdManager.getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(null, existingItems).isEmpty());
        assertTrue(TradeByItemIdManager.getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(tradeByItemIdManagers, null).isEmpty());
        assertTrue(TradeByItemIdManager.getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(new ArrayList<>(), existingItems).isEmpty());
        assertTrue(TradeByItemIdManager.getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(tradeByItemIdManagers, new ArrayList<>()).isEmpty());
    }

    @Test
    public void getItemForCentralTradeManagerFromTradeByItemsIdManagersByPriority_should_return_items_by_priority() {
        TradeByItemIdManager tradeByItemIdManager1 = new TradeByItemIdManager();
        tradeByItemIdManager1.setItemId("item1");
        tradeByItemIdManager1.setTradeOperationType(TradeOperationType.BUY);
        tradeByItemIdManager1.setPriorityMultiplier(1);

        TradeByItemIdManager tradeByItemIdManager2 = new TradeByItemIdManager();
        tradeByItemIdManager2.setItemId("item2");
        tradeByItemIdManager2.setTradeOperationType(TradeOperationType.SELL);
        tradeByItemIdManager2.setPriorityMultiplier(1);

        TradeByItemIdManager tradeByItemIdManager3 = new TradeByItemIdManager();
        tradeByItemIdManager3.setItemId("item2");
        tradeByItemIdManager3.setTradeOperationType(TradeOperationType.SELL);
        tradeByItemIdManager3.setPriorityMultiplier(2);

        TradeByItemIdManager tradeByItemIdManager4 = new TradeByItemIdManager();
        tradeByItemIdManager4.setItemId("item4");
        tradeByItemIdManager4.setTradeOperationType(TradeOperationType.SELL);
        tradeByItemIdManager4.setPriorityMultiplier(1);

        List<TradeByItemIdManager> tradeByItemIdManagers = List.of(tradeByItemIdManager1, tradeByItemIdManager2, tradeByItemIdManager3, tradeByItemIdManager4);
        List<ItemEntityDTO> existingItems = List.of(new ItemEntityDTO("item1"), new ItemEntityDTO("item2"), new ItemEntityDTO("item3"));

        Set<PersonalItem> result = TradeByItemIdManager.getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(tradeByItemIdManagers, existingItems);

        assertEquals(2, result.size());
        assertTrue(result.contains(new PersonalItem(new ItemEntityDTO("item1"), tradeByItemIdManager1)));
        assertTrue(result.contains(new PersonalItem(new ItemEntityDTO("item2"), tradeByItemIdManager3)));
    }

    @Test
    public void toItemForCentralTradeManagerDtoOrNull_should_return_null_when_existingItems_doesnt_contain_item_id() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("itemId");

        ItemEntityDTO item = new ItemEntityDTO();
        item.setItemId("anotherItemId");

        assertNull(tradeByItemIdManager.toItemForCentralTradeManagerDtoOrNull(List.of(item)));
    }

    @Test
    public void toItemForCentralTradeManagerDtoOrNull_should_return_ItemForCentralTradeManagerDTO_when_existingItems_contains_item_id() {
        TradeByItemIdManager tradeByItemIdManager = new TradeByItemIdManager();
        tradeByItemIdManager.setItemId("itemId");
        tradeByItemIdManager.setTradeOperationType(TradeOperationType.BUY);
        tradeByItemIdManager.setEnabled(true);
        tradeByItemIdManager.setBuyBoundaryPrice(100);
        tradeByItemIdManager.setSellBoundaryPrice(200);
        tradeByItemIdManager.setPriorityMultiplier(1);

        ItemEntityDTO item = new ItemEntityDTO();
        item.setItemId("itemId");
        item.setName("itemName");
        item.setAssetUrl("assetUrl");
        item.setTags(List.of("tag1", "tag2"));
        item.setRarity(ItemRarity.UNCOMMON);
        item.setType(ItemType.WeaponSkin);
        item.setMaxBuyPrice(300);
        item.setBuyOrdersCount(400);
        item.setMinSellPrice(500);
        item.setSellOrdersCount(600);
        item.setLastSoldAt(LocalDateTime.now());
        item.setLastSoldPrice(700);
        item.setMonthAveragePrice(800);
        item.setMonthMedianPrice(900);
        item.setMonthMaxPrice(1000);
        item.setMonthMinPrice(1100);
        item.setMonthSalesPerDay(1200);
        item.setDayAveragePrice(1300);
        item.setDayMedianPrice(1400);
        item.setDayMaxPrice(1500);
        item.setDayMinPrice(1600);
        item.setDaySales(1700);
        item.setPriceToSellIn1Hour(1800);
        item.setPriceToSellIn6Hours(1900);
        item.setPriceToSellIn24Hours(2000);
        item.setPriceToSellIn168Hours(2100);
        item.setPriceToBuyIn1Hour(2200);
        item.setPriceToBuyIn6Hours(2300);
        item.setPriceToBuyIn24Hours(2400);
        item.setPriceToBuyIn168Hours(2500);

        assertEquals(tradeByItemIdManager.toItemForCentralTradeManagerDtoOrNull(List.of(item)), new PersonalItem(item, tradeByItemIdManager));
    }
}