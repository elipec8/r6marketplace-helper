package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStatsEntityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemDaySalesUbiStatsUbiStatsEntityDTOPostgresServiceTest {
    @Autowired
    private ItemDaySalesUbiStatsPostgresService itemDaySalesUbiStatsPostgresService;
    @SpyBean
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsPostgresRepository;
    @SpyBean
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    @Transactional
    public void setUp() {
        itemDaySalesUbiStatsPostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void saveAll_should_save_sale_stats_from_each_statList() {
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO1 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.EPOCH, 1, 2, 3, 4);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO2 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(2023, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO3 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(9999, 1, 1), 3, 4, 5, 6);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats1 = new GroupedItemDaySalesUbiStats("item1", List.of(itemDaySalesUbiStatsEntityDTO1, itemDaySalesUbiStatsEntityDTO2, itemDaySalesUbiStatsEntityDTO3));

        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO4 = new ItemDaySalesUbiStatsEntityDTO("item2", LocalDate.EPOCH, 4, 5, 6, 7);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats2 = new GroupedItemDaySalesUbiStats("item2", List.of(itemDaySalesUbiStatsEntityDTO4));

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats3 = new GroupedItemDaySalesUbiStats("item3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("item1"), new ItemEntity("item2"), new ItemEntity("item3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(groupedItemDaySalesUbiStats1, groupedItemDaySalesUbiStats2, groupedItemDaySalesUbiStats3));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item1").size());
        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_create_new_stats_and_update_old() {
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO1 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.EPOCH, 1, 2, 3, 4);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO2 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(2023, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO3 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(9999, 1, 1), 3, 4, 5, 6);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO3Updated = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(9999, 1, 1), 4, 5, 6, 7);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats1 = new GroupedItemDaySalesUbiStats("item1", List.of(itemDaySalesUbiStatsEntityDTO1, itemDaySalesUbiStatsEntityDTO2, itemDaySalesUbiStatsEntityDTO3));

        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO4 = new ItemDaySalesUbiStatsEntityDTO("item2", LocalDate.of(9999, 1, 1), 5, 6, 7, 8);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats2 = new GroupedItemDaySalesUbiStats("item2", List.of(itemDaySalesUbiStatsEntityDTO4));

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats3 = new GroupedItemDaySalesUbiStats("item3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("item1"), new ItemEntity("item2"), new ItemEntity("item3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(groupedItemDaySalesUbiStats1, groupedItemDaySalesUbiStats2,
                groupedItemDaySalesUbiStats3));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(new GroupedItemDaySalesUbiStats("item1", List.of(itemDaySalesUbiStatsEntityDTO3Updated))));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item1").size());

        assertTrue(itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item1").stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList().contains(itemDaySalesUbiStatsEntityDTO3Updated));

        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_skip_if_item_doesnt_exist() {
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO1 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.EPOCH, 1, 2, 3, 4);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO2 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(2023, 1, 1), 2, 3, 4, 5);
        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO3 = new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.of(9999, 1, 1), 3, 4, 5, 6);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats1 = new GroupedItemDaySalesUbiStats("item1", List.of(itemDaySalesUbiStatsEntityDTO1, itemDaySalesUbiStatsEntityDTO2, itemDaySalesUbiStatsEntityDTO3));

        ItemDaySalesUbiStatsEntityDTO itemDaySalesUbiStatsEntityDTO4 = new ItemDaySalesUbiStatsEntityDTO("item2", LocalDate.of(9999, 1, 1), 5, 6, 7, 8);

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats2 = new GroupedItemDaySalesUbiStats("item2", List.of(itemDaySalesUbiStatsEntityDTO4));

        GroupedItemDaySalesUbiStats groupedItemDaySalesUbiStats3 = new GroupedItemDaySalesUbiStats("item3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("item1"), new ItemEntity("item3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(groupedItemDaySalesUbiStats1, groupedItemDaySalesUbiStats2, groupedItemDaySalesUbiStats3));

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item1").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("item3").size());

        assertEquals(2, itemPostgresRepository.count());
    }

    @Test
    public void findAll_should_return_mapped_result_from_repository() {
        List<ItemDaySalesUbiStatsEntity> expected = new ArrayList<>();
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item1"), new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.EPOCH, 1, 2, 3, 4)));
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item2"), new ItemDaySalesUbiStatsEntityDTO("item2", LocalDate.of(2023, 1, 1), 2, 3,
                4, 5)));
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item3"), new ItemDaySalesUbiStatsEntityDTO("item3", LocalDate.of(9999, 1, 1), 3, 4, 5, 6)));

        when(itemDaySalesUbiStatsPostgresRepository.findAll()).thenReturn(expected);

        List<ItemDaySalesUbiStatsEntityDTO> expectedMapped = expected.stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList();
        List<ItemDaySalesUbiStatsEntityDTO> actual = itemDaySalesUbiStatsPostgresService.findAll();

        assertTrue(actual.containsAll(expectedMapped) && expectedMapped.containsAll(actual));
    }

    @Test
    public void findAllForLastMonth_should_return_mapped_result_from_repository() {
        List<ItemDaySalesUbiStatsEntity> expected = new ArrayList<>();
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item1"), new ItemDaySalesUbiStatsEntityDTO("item1", LocalDate.EPOCH, 1, 2, 3, 4)));
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item2"), new ItemDaySalesUbiStatsEntityDTO("item2", LocalDate.of(2023, 1, 1), 2, 3,
                4, 5)));
        expected.add(new ItemDaySalesUbiStatsEntity(new ItemEntity("item3"), new ItemDaySalesUbiStatsEntityDTO("item3", LocalDate.of(9999, 1, 1), 3, 4, 5, 6)));

        when(itemDaySalesUbiStatsPostgresRepository.findAllForLastMonth()).thenReturn(expected);

        List<ItemDaySalesUbiStatsEntityDTO> expectedMapped = expected.stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList();
        List<ItemDaySalesUbiStatsEntityDTO> actual = itemDaySalesUbiStatsPostgresService.findAllForLastMonth();

        assertTrue(actual.containsAll(expectedMapped) && expectedMapped.containsAll(actual));
    }
}