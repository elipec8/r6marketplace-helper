package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.dtos.ItemDaySales;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemDaySalesUbiStatsPostgresServiceTest {
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
        ItemDaySales itemDaySales1 = new ItemDaySales(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySales itemDaySales2 = new ItemDaySales(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySales itemDaySales3 = new ItemDaySales(new Date(1000000000), 3, 4, 5, 6, 7);

        ItemSaleUbiStats itemSaleUbiStats1 = new ItemSaleUbiStats("itemId1", List.of(itemDaySales1, itemDaySales2, itemDaySales3));

        ItemDaySales itemDaySales4 = new ItemDaySales(new Date(3), 4, 5, 6, 7, 8);

        ItemSaleUbiStats itemSaleUbiStats2 = new ItemSaleUbiStats("itemId2", List.of(itemDaySales4));

        ItemSaleUbiStats itemSaleUbiStats3 = new ItemSaleUbiStats("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(itemSaleUbiStats1, itemSaleUbiStats2, itemSaleUbiStats3));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());
        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_create_new_stats_and_update_old() {
        ItemDaySales itemDaySales1 = new ItemDaySales(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySales itemDaySales2 = new ItemDaySales(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySales itemDaySales3 = new ItemDaySales(new Date(1000000000), 3, 4, 5, 6, 7);
        ItemDaySales itemDaySales3Updated = new ItemDaySales(new Date(1000000000), 1, 5, 4, 3, 2);

        ItemSaleUbiStats itemSaleUbiStats1 = new ItemSaleUbiStats("itemId1", List.of(itemDaySales1, itemDaySales2, itemDaySales3));

        ItemDaySales itemDaySales4 = new ItemDaySales(new Date(3), 4, 5, 6, 7, 8);

        ItemSaleUbiStats itemSaleUbiStats2 = new ItemSaleUbiStats("itemId2", List.of(itemDaySales4));

        ItemSaleUbiStats itemSaleUbiStats3 = new ItemSaleUbiStats("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId2"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(itemSaleUbiStats1, itemSaleUbiStats2, itemSaleUbiStats3));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(new ItemSaleUbiStats("itemId1", List.of(itemDaySales3Updated))));

        assertEquals(4, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());

        assertTrue(itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").stream().anyMatch(itemDaySales -> itemDaySales.toItemDaySales().equals(itemDaySales3Updated)));

        assertEquals(1, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(3, itemPostgresRepository.count());
    }

    @Test
    public void saveAll_should_not_save_or_throw_if_item_doesnt_exist() {
        ItemDaySales itemDaySales1 = new ItemDaySales(new Date(0), 1, 2, 3, 4, 5);
        ItemDaySales itemDaySales2 = new ItemDaySales(new Date(100000), 2, 3, 4, 5, 6);
        ItemDaySales itemDaySales3 = new ItemDaySales(new Date(1000000000), 3, 4, 5, 6, 7);

        ItemSaleUbiStats itemSaleUbiStats1 = new ItemSaleUbiStats("itemId1", List.of(itemDaySales1, itemDaySales2, itemDaySales3));

        ItemDaySales itemDaySales4 = new ItemDaySales(new Date(3), 4, 5, 6, 7, 8);

        ItemSaleUbiStats itemSaleUbiStats2 = new ItemSaleUbiStats("itemId2", List.of(itemDaySales4));

        ItemSaleUbiStats itemSaleUbiStats3 = new ItemSaleUbiStats("itemId3", List.of());

        itemPostgresRepository.saveAll(List.of(new ItemEntity("itemId1"), new ItemEntity("itemId3")));

        itemDaySalesUbiStatsPostgresService.saveAll(List.of(itemSaleUbiStats1, itemSaleUbiStats2, itemSaleUbiStats3));

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.count());

        assertEquals(3, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId1").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId2").size());
        assertEquals(0, itemDaySalesUbiStatsPostgresRepository.findAllByItemItemId("itemId3").size());

        assertEquals(2, itemPostgresRepository.count());
    }
}