package github.ricemonger.item_trade_stats_calculator.postgres.services;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemDaySalesUbiStatsDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemDaySalesUbiStatsEntityMapper;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemDaySalesUbiStatsPostgresServiceTest {
    @Autowired
    private ItemDaySalesUbiStatsPostgresService itemDaySalesUbiStatsPostgresService;
    @MockBean
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;
    @MockBean
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Test
    public void findAllForLastMonth_should_return_mapped_repository_result() {
        ItemDaySalesUbiStatsDtoProjection projection1 = Mockito.mock(ItemDaySalesUbiStatsDtoProjection.class);
        ItemDaySalesUbiStatsDtoProjection projection2 = Mockito.mock(ItemDaySalesUbiStatsDtoProjection.class);

        ItemDaySalesUbiStats dto1 = Mockito.mock(ItemDaySalesUbiStats.class);
        ItemDaySalesUbiStats dto2 = Mockito.mock(ItemDaySalesUbiStats.class);

        Mockito.when(itemDaySalesUbiStatsRepository.findAllForLastMonth()).thenReturn(List.of(projection1, projection2));
        Mockito.when(itemDaySalesUbiStatsEntityMapper.createDTO(projection1)).thenReturn(dto1);
        Mockito.when(itemDaySalesUbiStatsEntityMapper.createDTO(projection2)).thenReturn(dto2);

        List<ItemDaySalesUbiStats> result = itemDaySalesUbiStatsPostgresService.findAllForLastMonth();

        assertEquals(2, result.size());
        assertTrue(result.contains(dto1));
        assertTrue(result.contains(dto2));
    }
}