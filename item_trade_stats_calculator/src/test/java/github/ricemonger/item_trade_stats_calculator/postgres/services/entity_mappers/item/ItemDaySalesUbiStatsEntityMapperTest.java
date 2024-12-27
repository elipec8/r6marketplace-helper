package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemIdEntity;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemDaySalesUbiStatsEntityMapperTest {
    @Autowired
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Test
    public void createDTO_should_return_expected_result() {
        ItemDaySalesUbiStatsEntity itemDaySalesUbiStatsEntity = new ItemDaySalesUbiStatsEntity();
        itemDaySalesUbiStatsEntity.setItem(new ItemIdEntity("itemId"));
        itemDaySalesUbiStatsEntity.setDate(LocalDate.of(2021, 9, 1));
        itemDaySalesUbiStatsEntity.setLowestPrice(1);
        itemDaySalesUbiStatsEntity.setAveragePrice(2);
        itemDaySalesUbiStatsEntity.setHighestPrice(3);
        itemDaySalesUbiStatsEntity.setItemsCount(4);

        ItemDaySalesUbiStats result = itemDaySalesUbiStatsEntityMapper.createDTO(itemDaySalesUbiStatsEntity);

        assertEquals("itemId", result.getItemId());
        assertEquals(LocalDate.of(2021, 9, 1), result.getDate());
        assertEquals(1, result.getLowestPrice());
        assertEquals(2, result.getAveragePrice());
        assertEquals(3, result.getHighestPrice());
        assertEquals(4, result.getItemsCount());
    }
}