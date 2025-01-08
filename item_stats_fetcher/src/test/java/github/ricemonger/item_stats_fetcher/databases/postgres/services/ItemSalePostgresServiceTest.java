package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers.ItemSalePostgresMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemSalePostgresServiceTest {
    @Autowired
    private ItemSalePostgresService itemSalePostgresService;
    @MockBean
    private ItemSalePostgresRepository itemSaleRepository;
    @MockBean
    private ItemSalePostgresMapper itemSalePostgresMapper;

    @Test
    public void saveAllItemsLastSales_should_insert_mapped_result() {
        List dtos = Mockito.mock(List.class);
        List entities = Mockito.mock(List.class);
        when(itemSalePostgresMapper.mapToSaleEntities(dtos)).thenReturn(entities);

        itemSalePostgresService.insertAllItemsLastSales(dtos);

        verify(itemSaleRepository).insertAll(entities);
    }

}