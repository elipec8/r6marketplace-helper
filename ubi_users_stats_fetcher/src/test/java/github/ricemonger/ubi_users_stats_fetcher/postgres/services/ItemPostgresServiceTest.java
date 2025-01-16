package github.ricemonger.ubi_users_stats_fetcher.postgres.services;

import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.CustomItemPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.common.ItemEntityMapper;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {
    @Autowired
    private ItemPostgresService itemPostgresService;
    @MockBean
    private CustomItemPostgresRepository customItemPostgresRepository;
    @MockBean
    private ItemEntityMapper itemEntityMapper;

    @Test
    public void findItemsByIds_should_return_mapped_repository_result() {
        ItemEntity itemEntity1 = mock(ItemEntity.class);
        ItemEntity itemEntity2 = mock(ItemEntity.class);

        Item item1 = mock(Item.class);
        Item item2 = mock(Item.class);

        List list = mock(List.class);

        when(customItemPostgresRepository.findAllByItemIdIn(same(list))).thenReturn(List.of(itemEntity1, itemEntity2));
        when(itemEntityMapper.createItem(itemEntity1)).thenReturn(item1);
        when(itemEntityMapper.createItem(itemEntity2)).thenReturn(item2);

        List<Item> items = itemPostgresService.findItemsByIds(list);

        assertEquals(2, items.size());
        assertTrue(items.stream().anyMatch(i -> i == item1));
        assertTrue(items.stream().anyMatch(i -> i == item2));
    }
}