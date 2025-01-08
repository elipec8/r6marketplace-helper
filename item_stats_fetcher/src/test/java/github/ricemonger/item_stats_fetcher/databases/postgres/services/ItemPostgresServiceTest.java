package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemMainFieldsPostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers.ItemMainFieldsPostgresMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {
    @Autowired
    private ItemPostgresService itemPostgresService;
    @MockBean
    private ItemMainFieldsPostgresRepository itemMainFieldsRepository;
    @MockBean
    private ItemMainFieldsPostgresMapper itemMainFieldsPostgresMapper;

    @Test
    public void saveAllItemsMainFields_should_save_mapped_result() {
        List dtos = Mockito.mock(List.class);
        List entities = Mockito.mock(List.class);
        when(itemMainFieldsPostgresMapper.mapToEntities(dtos)).thenReturn(entities);

        itemPostgresService.saveAllItemsMainFields(dtos);

        verify(itemMainFieldsRepository).saveAll(entities);
    }

    @Test
    public void updateAllItemsMainFieldsExceptTags_should_update_all_items_main_fields_except_tags() {
        List dtos = Mockito.mock(List.class);
        List entities = Mockito.mock(List.class);
        when(itemMainFieldsPostgresMapper.mapToEntities(dtos)).thenReturn(entities);

        itemPostgresService.updateAllItemsMainFieldsExceptTags(dtos);

        verify(itemMainFieldsRepository).updateAllExceptTagsFields(entities);
    }

    @Test
    public void updateAllItemsMinSellPrice_should_update_all_items_min_sell_price() {
        List dtos = Mockito.mock(List.class);

        itemPostgresService.updateAllItemsMinSellPrice(dtos);

        verify(itemMainFieldsRepository).updateAllItemsMinSellPrice(dtos);
    }
}