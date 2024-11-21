package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ItemDaySalesUbiStatsEntityTest {
    @Test
    public void toItemDaySalesUbiStats_should_properly_map_with_all_fields() {
        ItemEntity item = new ItemEntity();
        item.setItemId("1");

        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity();
        entity.setItem(item);
        entity.setDate(LocalDate.of(2023, 1, 1));
        entity.setLowestPrice(100);
        entity.setAveragePrice(200);
        entity.setHighestPrice(300);
        entity.setItemsCount(10);

        ItemDaySalesUbiStats expected = new ItemDaySalesUbiStats();
        expected.setItemId("1");
        expected.setDate(LocalDate.of(2023, 1, 1));
        expected.setLowestPrice(100);
        expected.setAveragePrice(200);
        expected.setHighestPrice(300);
        expected.setItemsCount(10);

        ItemDaySalesUbiStats actual = entity.toItemDaySalesUbiStats();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_map_with_all_fields() {
        ItemEntity item = new ItemEntity();
        item.setItemId("1");

        ItemDaySalesUbiStats itemDaySalesUbiStats = new ItemDaySalesUbiStats();
        itemDaySalesUbiStats.setDate(LocalDate.of(2023, 2, 1));
        itemDaySalesUbiStats.setLowestPrice(100);
        itemDaySalesUbiStats.setAveragePrice(200);
        itemDaySalesUbiStats.setHighestPrice(300);
        itemDaySalesUbiStats.setItemsCount(10);

        ItemDaySalesUbiStatsEntity expected = new ItemDaySalesUbiStatsEntity();
        expected.setItem(item);
        expected.setDate(LocalDate.of(2023, 2, 1));
        expected.setLowestPrice(100);
        expected.setAveragePrice(200);
        expected.setHighestPrice(300);
        expected.setItemsCount(10);

        ItemDaySalesUbiStatsEntity actual = new ItemDaySalesUbiStatsEntity(item, itemDaySalesUbiStats);

        assertEquals(expected, actual);
    }
}