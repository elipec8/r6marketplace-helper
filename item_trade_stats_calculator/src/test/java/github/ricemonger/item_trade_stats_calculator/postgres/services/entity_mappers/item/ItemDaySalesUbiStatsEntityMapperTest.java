package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemDaySalesUbiStatsProjection;
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
        ItemDaySalesUbiStatsProjection projection = new ItemDaySalesUbiStatsProjection();
        projection.setItemId("itemId");
        projection.setDate(LocalDate.of(2021, 9, 1));
        projection.setLowestPrice(1);
        projection.setAveragePrice(2);
        projection.setHighestPrice(3);
        projection.setItemsCount(4);

        ItemDaySalesUbiStats result = itemDaySalesUbiStatsEntityMapper.createDTO(projection);

        assertEquals("itemId", result.getItemId());
        assertEquals(LocalDate.of(2021, 9, 1), result.getDate());
        assertEquals(1, result.getLowestPrice());
        assertEquals(2, result.getAveragePrice());
        assertEquals(3, result.getHighestPrice());
        assertEquals(4, result.getItemsCount());
    }
}