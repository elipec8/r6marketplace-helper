package github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemMainFieldsPostgresRepository;
import github.ricemonger.utils.DTOs.common.ItemSale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemSalePostgresMapperTest {

    @Autowired
    private ItemSalePostgresMapper itemSalePostgresMapper;

    @MockBean
    private ItemMainFieldsPostgresRepository itemMainFieldsRepository;

    @Test
    public void createEntities_should_properly_map_entities() {
        ItemSale sale1 = new ItemSale("itemId1", LocalDateTime.of(2021, 1, 1, 0, 0), 100);
        ItemSale sale2 = new ItemSale("itemId2", LocalDateTime.of(2022, 1, 1, 0, 0), 200);
        ItemSale sale3 = new ItemSale("itemId3", LocalDateTime.of(2023, 1, 1, 0, 0), 300);

        when(itemMainFieldsRepository.findAllItemIds()).thenReturn(new HashSet<>(Set.of("itemId1", "itemId2", "itemId4")));
        when(itemMainFieldsRepository.getReferenceById("itemId1")).thenReturn(new ItemMainFieldsEntity("itemId1"));
        when(itemMainFieldsRepository.getReferenceById("itemId2")).thenReturn(new ItemMainFieldsEntity("itemId2"));
        when(itemMainFieldsRepository.getReferenceById("itemId3")).thenReturn(new ItemMainFieldsEntity("itemId3"));
        when(itemMainFieldsRepository.getReferenceById("itemId4")).thenReturn(new ItemMainFieldsEntity("itemId4"));

        List<ItemSaleEntity> expected = new ArrayList<>();
        expected.add(new ItemSaleEntity(new ItemMainFieldsEntity("itemId1"), LocalDateTime.of(2021, 1, 1, 0, 0), 100));
        expected.add(new ItemSaleEntity(new ItemMainFieldsEntity("itemId2"), LocalDateTime.of(2022, 1, 1, 0, 0), 200));

        List<ItemSaleEntity> actual = itemSalePostgresMapper.mapToSaleEntities(List.of(sale1, sale2, sale3));

        assertTrue(expected.stream().allMatch(ex -> actual.stream().anyMatch(ac -> ac.isFullyEqual(ex))) && expected.size() == actual.size());
    }
}