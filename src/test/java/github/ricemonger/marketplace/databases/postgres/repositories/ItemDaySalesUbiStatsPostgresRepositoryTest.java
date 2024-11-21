package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ItemDaySalesUbiStatsPostgresRepositoryTest {
    @SpyBean
    private ItemDaySalesUbiStatsPostgresRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(item1);
        entity1.setDate(LocalDate.now());

        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(item2);
        entity2.setDate(LocalDate.now().minusDays(29));

        ItemEntity item3 = new ItemEntity();
        item3.setItemId("item3");
        ItemDaySalesUbiStatsEntity entity3 = new ItemDaySalesUbiStatsEntity();
        entity3.setItem(item3);
        entity3.setDate(LocalDate.now().minusDays(30));

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<ItemDaySalesUbiStatsEntity> result = repository.findAllLastMonthSales();
        for (ItemDaySalesUbiStatsEntity entity : result) {
            assertNotEquals(entity3.getItem().getItemId(), entity.getItem().getItemId());
        }

        assertEquals(2, result.size());
    }
}