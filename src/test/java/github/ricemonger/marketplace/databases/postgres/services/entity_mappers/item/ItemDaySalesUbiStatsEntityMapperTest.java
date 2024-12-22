package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemDaySalesUbiStatsEntityMapperTest {
    @Autowired
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;
    @MockBean
    private ItemPostgresRepository itemPostgresRepository;

    @Test
    public void createDTO_should_properly_map_entity_to_dto() {
        ItemDaySalesUbiStatsEntity entity = new ItemDaySalesUbiStatsEntity(new ItemEntity("itemId"), LocalDate.of(2021, 1, 1), 1, 2, 3, 4);

        ItemDaySalesUbiStats actual = itemDaySalesUbiStatsEntityMapper.createDTO(entity);

        assertEquals("itemId", actual.getItemId());
        assertEquals(LocalDate.of(2021, 1, 1), actual.getDate());
        assertEquals(1, actual.getLowestPrice());
        assertEquals(2, actual.getAveragePrice());
        assertEquals(3, actual.getHighestPrice());
        assertEquals(4, actual.getItemsCount());
    }

    @Test
    public void createEntities_should_return_empty_list_when_input_is_empty() {
        assertTrue(itemDaySalesUbiStatsEntityMapper.createEntities(null).isEmpty());
        assertTrue(itemDaySalesUbiStatsEntityMapper.createEntities(Set.of()).isEmpty());
    }

    @Test
    public void createEntities_should_return_mapped_entities_except_non_existent_items() {
        when(itemPostgresRepository.findAllItemIds()).thenReturn(new HashSet<>(Set.of("itemId1", "itemId2", "itemId4")));
        when(itemPostgresRepository.getReferenceById("itemId1")).thenReturn(new ItemEntity("itemId1"));
        when(itemPostgresRepository.getReferenceById("itemId2")).thenReturn(new ItemEntity("itemId2"));
        when(itemPostgresRepository.getReferenceById("itemId3")).thenReturn(new ItemEntity("itemId3"));
        when(itemPostgresRepository.getReferenceById("itemId4")).thenReturn(new ItemEntity("itemId4"));

        ItemDaySalesUbiStats daySales11 = new ItemDaySalesUbiStats("itemId1", LocalDate.of(2021, 1, 1), 1, 2, 3, 4);
        ItemDaySalesUbiStats daySales12 = new ItemDaySalesUbiStats("itemId1", LocalDate.of(2021, 1, 2), 5, 6, 7, 8);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats1 = new GroupedItemDaySalesUbiStats("itemId1", List.of(daySales11, daySales12));

        ItemDaySalesUbiStats daySales21 = new ItemDaySalesUbiStats("itemId2", LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStats daySales22 = new ItemDaySalesUbiStats("itemId2", LocalDate.of(2022, 1, 2), 6, 7, 8, 9);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats2 = new GroupedItemDaySalesUbiStats("itemId2", List.of(daySales21, daySales22));

        ItemDaySalesUbiStats daySales31 = new ItemDaySalesUbiStats("itemId3", LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStats daySales32 = new ItemDaySalesUbiStats("itemId3", LocalDate.of(2022, 1, 2), 6, 7, 8, 9);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats3 = new GroupedItemDaySalesUbiStats("itemId2", List.of(daySales31, daySales32));

        Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList = List.of(groupedItemDaySalesUbiStats1, groupedItemDaySalesUbiStats2, groupedItemDaySalesUbiStats3);

        ItemDaySalesUbiStatsEntity expected1 = new ItemDaySalesUbiStatsEntity(new ItemEntity("itemId1"), LocalDate.of(2021, 1, 1), 1, 2, 3, 4);
        ItemDaySalesUbiStatsEntity expected2 = new ItemDaySalesUbiStatsEntity(new ItemEntity("itemId1"), LocalDate.of(2021, 1, 2), 5, 6, 7, 8);
        ItemDaySalesUbiStatsEntity expected3 = new ItemDaySalesUbiStatsEntity(new ItemEntity("itemId2"), LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStatsEntity expected4 = new ItemDaySalesUbiStatsEntity(new ItemEntity("itemId2"), LocalDate.of(2022, 1, 2), 6, 7, 8, 9);

        List<ItemDaySalesUbiStatsEntity> expected = List.of(expected1, expected2, expected3, expected4);

        List<ItemDaySalesUbiStatsEntity> actual = itemDaySalesUbiStatsEntityMapper.createEntities(groupedItemDaySalesUbiStatsList);

        System.out.println("Actual:");
        for (ItemDaySalesUbiStatsEntity entity : actual) {
            System.out.println(entity);
        }

        System.out.println("Expected:");
        for (ItemDaySalesUbiStatsEntity entity : expected) {
            System.out.println(entity);
        }

        assertTrue(expected.stream().allMatch(ex -> actual.stream().anyMatch(ac -> ac.isFullyEqual(ex))) && expected.size() == actual.size());
    }
}