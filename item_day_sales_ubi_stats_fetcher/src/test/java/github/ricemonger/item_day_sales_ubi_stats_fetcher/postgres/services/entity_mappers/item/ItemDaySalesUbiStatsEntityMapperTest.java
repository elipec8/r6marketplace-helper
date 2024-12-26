package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services.entity_mappers.item;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item.ItemIdEntity;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemDaySalesUbiStatsEntityMapperTest {
    @Autowired
    private ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;
    @MockBean
    private ItemPostgresRepository itemPostgresRepository;

    @Test
    public void createEntities_should_return_empty_list_when_input_is_empty() {
        assertTrue(itemDaySalesUbiStatsEntityMapper.createEntities(null).isEmpty());
        assertTrue(itemDaySalesUbiStatsEntityMapper.createEntities(Set.of()).isEmpty());
    }

    @Test
    public void createEntities_should_return_mapped_entities_except_non_existent_items() {
        List<ItemIdEntity> entities = new ArrayList<>();
        entities.add(new ItemIdEntity("itemId1"));
        entities.add(new ItemIdEntity("itemId2"));
        entities.add(new ItemIdEntity("itemId4"));
        when(itemPostgresRepository.findAll()).thenReturn(entities);

        ItemDaySalesUbiStats daySales11 = new ItemDaySalesUbiStats("itemId1", LocalDate.of(2021, 1, 1), 1, 2, 3, 4);
        ItemDaySalesUbiStats daySales12 = new ItemDaySalesUbiStats("itemId1", LocalDate.of(2021, 1, 2), 5, 6, 7, 8);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats1 = new GroupedItemDaySalesUbiStats("itemId1", List.of(daySales11, daySales12));

        ItemDaySalesUbiStats daySales21 = new ItemDaySalesUbiStats("itemId2", LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStats daySales22 = new ItemDaySalesUbiStats("itemId2", LocalDate.of(2022, 1, 2), 6, 7, 8, 9);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats2 = new GroupedItemDaySalesUbiStats("itemId2", List.of(daySales21, daySales22));

        ItemDaySalesUbiStats daySales31 = new ItemDaySalesUbiStats("itemId3", LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStats daySales32 = new ItemDaySalesUbiStats("itemId3", LocalDate.of(2022, 1, 2), 6, 7, 8, 9);
        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats3 = new GroupedItemDaySalesUbiStats("itemId3", List.of(daySales31, daySales32));

        Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList = List.of(groupedItemDaySalesUbiStats1, groupedItemDaySalesUbiStats2, groupedItemDaySalesUbiStats3);

        ItemDaySalesUbiStatsEntity expected1 = new ItemDaySalesUbiStatsEntity(new ItemIdEntity("itemId1"), LocalDate.of(2021, 1, 1), 1, 2, 3, 4);
        ItemDaySalesUbiStatsEntity expected2 = new ItemDaySalesUbiStatsEntity(new ItemIdEntity("itemId1"), LocalDate.of(2021, 1, 2), 5, 6, 7, 8);
        ItemDaySalesUbiStatsEntity expected3 = new ItemDaySalesUbiStatsEntity(new ItemIdEntity("itemId2"), LocalDate.of(2022, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStatsEntity expected4 = new ItemDaySalesUbiStatsEntity(new ItemIdEntity("itemId2"), LocalDate.of(2022, 1, 2), 6, 7, 8, 9);

        List<ItemDaySalesUbiStatsEntity> expected = List.of(expected1, expected2, expected3, expected4);

        List<ItemDaySalesUbiStatsEntity> actual = itemDaySalesUbiStatsEntityMapper.createEntities(groupedItemDaySalesUbiStatsList);

        System.out.println("expected: ");
        expected.forEach(System.out::println);
        System.out.println("actual: ");
        actual.forEach(System.out::println);

        assertTrue(expected.stream().allMatch(ex -> actual.stream().anyMatch(ac -> ac.isFullyEqual(ex))) && expected.size() == actual.size());
    }
}