package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.custom_entities.ItemMainFieldsEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.custom_entities.ItemSaleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CustomItemSalePostgresRepositoryTest {
    @SpyBean
    private CustomItemSalePostgresRepository customItemSalePostgresRepository;
    @SpyBean
    private CustomItemMainFieldsPostgresRepository itemMainFieldsPostgresRepository;

    @BeforeEach
    public void setUp() {
        customItemSalePostgresRepository.deleteAll();
        itemMainFieldsPostgresRepository.deleteAll();
    }

    @Disabled // H2 does not support ON CONFLICT DO NOTHING
    @Test
    @Transactional
    public void insertAll_should_insert_ItemSale_all_ItemSales_sales_and_skip_if_already_exists() {
        ItemMainFieldsEntity item1 = itemMainFieldsPostgresRepository.save(new ItemMainFieldsEntity("itemId1"));
        ItemMainFieldsEntity item2 = itemMainFieldsPostgresRepository.save(new ItemMainFieldsEntity("itemId2"));

        ItemSaleEntity existingEntity1 = new ItemSaleEntity(item1, LocalDateTime.of(2021, 1, 1, 1, 1), 1);

        customItemSalePostgresRepository.save(existingEntity1);

        ItemSaleEntity alreadyExistingEntity1 = new ItemSaleEntity(item1, LocalDateTime.of(2021, 1, 1, 1, 1), 2);
        ItemSaleEntity newEntity1 = new ItemSaleEntity(item1, LocalDateTime.of(2022, 1, 1, 1, 2), 1);

        ItemSaleEntity newEntity2 = new ItemSaleEntity(item2, LocalDateTime.of(2022, 1, 1, 1, 2), 2);

        ItemSaleEntity nonExistingItem3 = new ItemSaleEntity(new ItemMainFieldsEntity("itemId3"), LocalDateTime.of(2022, 1, 1, 1, 2), 3);

        customItemSalePostgresRepository.insertAll(List.of(alreadyExistingEntity1, newEntity1, newEntity2, nonExistingItem3));

        List<ItemSaleEntity> result = customItemSalePostgresRepository.findAll();

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(res -> res.isFullyEqual(existingEntity1)));
        assertTrue(result.stream().anyMatch(res -> res.isFullyEqual(newEntity1)));
        assertTrue(result.stream().anyMatch(res -> res.isFullyEqual(newEntity2)));
        assertTrue(result.stream().noneMatch(res -> res.isFullyEqual(nonExistingItem3)));
    }
}