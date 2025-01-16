package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories.CustomItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services.entity_mappers.item.ItemDaySalesUbiStatsEntityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
class ItemDaySalesUbiStatsPostgresDatabaseServiceTest {
    @Autowired
    private ItemDaySalesUbiStatsPostgresDatabaseService itemDaySalesUbiStatsPostgresDatabaseService;
    @MockBean
    private CustomItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;
    @MockBean
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Test
    public void saveAll_should_handle_to_service_mapped_entities() {
        List mockedList = mock(List.class);
        List entities = mock(List.class);
        when(itemDaySalesUbiStatsEntityMapper.createEntities(mockedList)).thenReturn(entities);

        itemDaySalesUbiStatsPostgresDatabaseService.saveAll(mockedList);

        verify(itemDaySalesUbiStatsRepository).saveAll(entities);
    }
}