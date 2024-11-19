package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemSalesUbiStatsByItemId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemDaySalesUbiStatsUbiStatsPostgresServiceTest {
    @Autowired
    private ItemDaySalesUbiStatsPostgresService itemDaySalesUbiStatsPostgresService;
    @Autowired
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsPostgresRepository;
    @Autowired
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    public void setUp() {
        itemDaySalesUbiStatsPostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void saveAll_should_save_sale_stats_from_each_statList() {
        ItemDaySalesUbiStats itemDaySalesUbiStats1 = new ItemDaySalesUbiStats(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySalesUbiStats itemDaySalesUbiStats2 = new ItemDaySalesUbiStats(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySalesUbiStats itemDaySalesUbiStats3 = new ItemDaySalesUbiStats(new Date(1000000000), 3, 4, 5, 6, 7);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId1 = new ItemSalesUbiStatsByItemId("itemId1", List.of(itemDaySalesUbiStats1, itemDaySalesUbiStats2, itemDaySalesUbiStats3));

        ItemDaySalesUbiStats itemDaySalesUbiStats4 = new ItemDaySalesUbiStats(new Date(3), 4, 5, 6, 7, 8);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId2 = new ItemSalesUbiStatsByItemId("itemId2", List.of(itemDaySalesUbiStats4));

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId3 = new ItemSalesUbiStatsByItemId("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAllSales(List.of(itemSalesUbiStatsByItemId1, itemSalesUbiStatsByItemId2, itemSalesUbiStatsByItemId3));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());
        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_create_new_stats_and_update_old() {
        ItemDaySalesUbiStats itemDaySalesUbiStats1 = new ItemDaySalesUbiStats(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySalesUbiStats itemDaySalesUbiStats2 = new ItemDaySalesUbiStats(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySalesUbiStats itemDaySalesUbiStats3 = new ItemDaySalesUbiStats(new Date(1000000000), 3, 4, 5, 6, 7);
        ItemDaySalesUbiStats itemDaySalesUbiStats3Updated = new ItemDaySalesUbiStats(new Date(1000000000), 1, 5, 4, 3, 2);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId1 = new ItemSalesUbiStatsByItemId("itemId1", List.of(itemDaySalesUbiStats1, itemDaySalesUbiStats2, itemDaySalesUbiStats3));

        ItemDaySalesUbiStats itemDaySalesUbiStats4 = new ItemDaySalesUbiStats(new Date(3), 4, 5, 6, 7, 8);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId2 = new ItemSalesUbiStatsByItemId("itemId2", List.of(itemDaySalesUbiStats4));

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId3 = new ItemSalesUbiStatsByItemId("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAllSales(List.of(itemSalesUbiStatsByItemId1, itemSalesUbiStatsByItemId2, itemSalesUbiStatsByItemId3));

        itemDaySalesUbiStatsPostgresService.saveAllSales(List.of(new ItemSalesUbiStatsByItemId("itemId1", List.of(itemDaySalesUbiStats3Updated))));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());

        System.out.println(itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList());
        System.out.println(itemDaySalesUbiStats3Updated);

        assertTrue(itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").stream().anyMatch(itemDaySales -> itemDaySales.toItemDaySalesUbiStats().equals(itemDaySalesUbiStats3Updated)));

        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_not_save_or_throw_if_item_doesnt_exist() {
        ItemDaySalesUbiStats itemDaySalesUbiStats1 = new ItemDaySalesUbiStats(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySalesUbiStats itemDaySalesUbiStats2 = new ItemDaySalesUbiStats(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySalesUbiStats itemDaySalesUbiStats3 = new ItemDaySalesUbiStats(new Date(1000000000), 3, 4, 5, 6, 7);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId1 = new ItemSalesUbiStatsByItemId("itemId1", List.of(itemDaySalesUbiStats1, itemDaySalesUbiStats2, itemDaySalesUbiStats3));

        ItemDaySalesUbiStats itemDaySalesUbiStats4 = new ItemDaySalesUbiStats(new Date(3), 4, 5, 6, 7, 8);

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId2 = new ItemSalesUbiStatsByItemId("itemId2", List.of(itemDaySalesUbiStats4));

        ItemSalesUbiStatsByItemId itemSalesUbiStatsByItemId3 = new ItemSalesUbiStatsByItemId("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAllSales(List.of(itemSalesUbiStatsByItemId1, itemSalesUbiStatsByItemId2, itemSalesUbiStatsByItemId3));

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(2, itemPostgresRepository.count());
    }
}