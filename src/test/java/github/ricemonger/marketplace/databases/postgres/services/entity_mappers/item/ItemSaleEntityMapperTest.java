package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemSale;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemSaleEntityMapperTest {
    @Autowired
    private ItemSaleEntityMapper itemSaleEntityMapper;
    @MockBean
    private ItemPostgresRepository itemRepository;

    @Test
    public void createDTO_should_properly_map_dto() {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItem(new ItemEntity("itemId"));
        entity.setSoldAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        entity.setPrice(100);

        ItemSale expected = new ItemSale("itemId", LocalDateTime.of(2022, 1, 1, 0, 0), 100);

        ItemSale actual = itemSaleEntityMapper.createDTO(entity);

        assertEquals(expected, actual);
    }

    @Test
    public void createEntities_should_properly_map_entities() {
        ItemSale sale1 = new ItemSale("itemId1", LocalDateTime.of(2021, 1, 1, 0, 0), 100);
        ItemSale sale2 = new ItemSale("itemId2", LocalDateTime.of(2022, 1, 1, 0, 0), 200);
        ItemSale sale3 = new ItemSale("itemId3", LocalDateTime.of(2023, 1, 1, 0, 0), 300);

        when(itemRepository.findAllItemIds()).thenReturn(new HashSet<>(Set.of("itemId1", "itemId2", "itemId4")));
        when(itemRepository.getReferenceById("itemId1")).thenReturn(new ItemEntity("itemId1"));
        when(itemRepository.getReferenceById("itemId2")).thenReturn(new ItemEntity("itemId2"));
        when(itemRepository.getReferenceById("itemId3")).thenReturn(new ItemEntity("itemId3"));
        when(itemRepository.getReferenceById("itemId4")).thenReturn(new ItemEntity("itemId4"));

        List<ItemSaleEntity> expected = new ArrayList<>();
        expected.add(new ItemSaleEntity(new ItemEntity("itemId1"), LocalDateTime.of(2021, 1, 1, 0, 0), 100));
        expected.add(new ItemSaleEntity(new ItemEntity("itemId2"), LocalDateTime.of(2022, 1, 1, 0, 0), 200));

        List<ItemSaleEntity> actual = itemSaleEntityMapper.createEntities(List.of(sale1, sale2, sale3));

        assertTrue(expected.stream().allMatch(ex -> actual.stream().anyMatch(ac -> ac.isFullyEqual(ex))) && expected.size() == actual.size());
    }
}