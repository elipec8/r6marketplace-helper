package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.ItemEntityMapper;
import github.ricemonger.utils.DTOs.common.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {
    @Autowired
    private ItemPostgresService itemService;
    @MockBean
    private ItemPostgresRepository itemRepository;
    @MockBean
    private ItemEntityMapper itemEntityMapper;

    @Test
    public void findById_should_return_mapped_entity() {
        ItemEntity itemEntity = new ItemEntity();
        when(itemRepository.findById("1")).thenReturn(java.util.Optional.of(itemEntity));
        Item item = new Item();
        when(itemEntityMapper.createDTO(same(itemEntity))).thenReturn(item);

        assertSame(item, itemService.findById("1"));
    }

    @Test
    public void findAll_should_return_mapped_entities() {
        ItemEntity entity1 = new ItemEntity();
        ItemEntity entity2 = new ItemEntity();
        List<ItemEntity> itemEntities = List.of(entity1, entity2);
        when(itemRepository.findAll()).thenReturn(itemEntities);
        Item item1 = new Item();
        Item item2 = new Item();
        List<Item> expected = List.of(item1, item2);
        when(itemEntityMapper.createDTO(same(entity1))).thenReturn(item1);
        when(itemEntityMapper.createDTO(same(entity2))).thenReturn(item2);

        List<Item> result = itemService.findAll();

        assertTrue(result.size() == expected.size() && result.stream().allMatch(res -> expected.stream().anyMatch(ex -> ex == res)));
    }
}