package github.ricemonger.marketplace.databases.postgres.entities.item;

import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStatsEntityDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ItemDaySalesUbiStatsEntityDTOEntityTest {
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

        ItemDaySalesUbiStatsEntityDTO expected = new ItemDaySalesUbiStatsEntityDTO();
        expected.setItemId("1");
        expected.setDate(LocalDate.of(2023, 1, 1));
        expected.setLowestPrice(100);
        expected.setAveragePrice(200);
        expected.setHighestPrice(300);
        expected.setItemsCount(10);

        ItemDaySalesUbiStatsEntityDTO actual = entity.toItemDaySalesUbiStats();

        assertEquals(expected, actual);
    }

    @Test
    public void constructor_should_properly_map_with_all_fields() {
        ItemEntity item = new ItemEntity();
        item.setItemId("1");

        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO = new ItemDaySalesUbiStatsEntityDTO();
        itemDaySalesUbiStatsEntityDTO.setDate(LocalDate.of(2023, 2, 1));
        itemDaySalesUbiStatsEntityDTO.setLowestPrice(100);
        itemDaySalesUbiStatsEntityDTO.setAveragePrice(200);
        itemDaySalesUbiStatsEntityDTO.setHighestPrice(300);
        itemDaySalesUbiStatsEntityDTO.setItemsCount(10);

        ItemDaySalesUbiStatsEntity expected = new ItemDaySalesUbiStatsEntity();
        expected.setItem(item);
        expected.setDate(LocalDate.of(2023, 2, 1));
        expected.setLowestPrice(100);
        expected.setAveragePrice(200);
        expected.setHighestPrice(300);
        expected.setItemsCount(10);

        ItemDaySalesUbiStatsEntity actual = new ItemDaySalesUbiStatsEntity(item, itemDaySalesUbiStatsEntityDTO);

        assertEquals(expected.getItem().getItemId(), actual.getItem().getItemId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getLowestPrice(), actual.getLowestPrice());
        assertEquals(expected.getAveragePrice(), actual.getAveragePrice());
        assertEquals(expected.getHighestPrice(), actual.getHighestPrice());
        assertEquals(expected.getItemsCount(), actual.getItemsCount());
    }
}