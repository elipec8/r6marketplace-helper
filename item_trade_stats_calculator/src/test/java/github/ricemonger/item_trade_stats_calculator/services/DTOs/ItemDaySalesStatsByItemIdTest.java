package github.ricemonger.item_trade_stats_calculator.services.DTOs;

import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemSale;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ItemDaySalesStatsByItemIdTest {

    @Test
    public void constructor_with_itemSales_should_properly_create_object_ignoring_different_itemId_sale_or_other_dates() {
        List<ItemSale> itemSales = new ArrayList<>();
        itemSales.add(new ItemSale("1", LocalDateTime.of(2024, 1, 1, 1, 0, 0), 1));
        itemSales.add(new ItemSale("1", LocalDateTime.of(2024, 1, 1, 2, 0, 0), 1));
        itemSales.add(new ItemSale("1", LocalDateTime.of(2024, 1, 1, 3, 0, 0), 2));
        itemSales.add(new ItemSale("1", LocalDateTime.of(2024, 1, 1, 4, 0, 0), 3));
        itemSales.add(new ItemSale("1", LocalDateTime.of(2024, 1, 2, 1, 0, 0), 4));
        itemSales.add(new ItemSale("2", LocalDateTime.of(2024, 1, 1, 5, 0, 0), 5));

        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId("1", LocalDate.of(2024, 1, 1), itemSales);

        assertEquals("1", itemDaySalesStatsByItemId.getItemId());
        assertEquals(LocalDate.of(2024, 1, 1), itemDaySalesStatsByItemId.getDate());
        assertEquals(3, itemDaySalesStatsByItemId.getPriceAndQuantity().size());
        assertEquals(2, itemDaySalesStatsByItemId.getPriceAndQuantity().get(1));
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(2));
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(3));
        assertNull(itemDaySalesStatsByItemId.getPriceAndQuantity().get(4));
        assertNull(itemDaySalesStatsByItemId.getPriceAndQuantity().get(5));
    }

    @Test
    public void constructor_with_ubiSaleStats_should_properly_create_object_ignoring_different_itemId_sale_or_other_dates_with_more_than_2_itemsCount() {
        List<ItemDaySalesUbiStats> daySalesUbiStats = new ArrayList<>();
        daySalesUbiStats.add(new ItemDaySalesUbiStats("1", LocalDate.of(2024, 1, 1), 100, 300, 500, 20));
        daySalesUbiStats.add(new ItemDaySalesUbiStats("1", LocalDate.of(2024, 1, 2), 200, 600, 1000, 40));
        daySalesUbiStats.add(new ItemDaySalesUbiStats("2", LocalDate.of(2024, 1, 1), 300, 900, 1500, 60));

        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId(LocalDate.of(2024, 1, 1), "1", daySalesUbiStats);

        assertEquals("1", itemDaySalesStatsByItemId.getItemId());
        assertEquals(LocalDate.of(2024, 1, 1), itemDaySalesStatsByItemId.getDate());
        assertEquals(3, itemDaySalesStatsByItemId.getPriceAndQuantity().size());
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(100));
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(500));
        assertEquals(18, itemDaySalesStatsByItemId.getPriceAndQuantity().get(300));
    }

    @Test
    public void constructor_with_ubiSaleStats_should_properly_create_object_with_1_item_count() {
        List<ItemDaySalesUbiStats> daySalesUbiStats = new ArrayList<>();
        daySalesUbiStats.add(new ItemDaySalesUbiStats("1", LocalDate.of(2024, 1, 1), 100, 300, 500, 1));

        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId(LocalDate.of(2024, 1, 1), "1", daySalesUbiStats);

        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().size());
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(300));
    }

    @Test
    public void constructor_with_ubiSaleStats_should_properly_create_object_with_2_item_count() {
        List<ItemDaySalesUbiStats> daySalesUbiStats = new ArrayList<>();
        daySalesUbiStats.add(new ItemDaySalesUbiStats("1", LocalDate.of(2024, 1, 1), 100, 300, 500, 2));

        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId(LocalDate.of(2024, 1, 1), "1", daySalesUbiStats);

        assertEquals(2, itemDaySalesStatsByItemId.getPriceAndQuantity().size());
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(100));
        assertEquals(1, itemDaySalesStatsByItemId.getPriceAndQuantity().get(500));
    }

    @Test
    public void getQuantity_should_return_correct_quantity() {
        Map<Integer, Integer> priceAndQuantity = new HashMap<>();
        priceAndQuantity.put(1, 10);
        priceAndQuantity.put(500, 5);
        priceAndQuantity.put(10000, 5);
        priceAndQuantity.put(1000000, 5);

        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId();
        itemDaySalesStatsByItemId.setPriceAndQuantity(priceAndQuantity);

        assertEquals(25, itemDaySalesStatsByItemId.getQuantity());
    }

    @Test
    public void getQuantity_should_return_0_when_priceAndQuantity_is_empty() {
        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId();
        itemDaySalesStatsByItemId.setPriceAndQuantity(new HashMap<>());

        assertEquals(0, itemDaySalesStatsByItemId.getQuantity());
    }

    @Test
    public void getQuantity_should_return_0_when_priceAndQuantity_is_null() {
        ItemDaySalesStatsByItemId itemDaySalesStatsByItemId = new ItemDaySalesStatsByItemId();
        itemDaySalesStatsByItemId.setPriceAndQuantity(null);

        assertEquals(0, itemDaySalesStatsByItemId.getQuantity());
    }

}