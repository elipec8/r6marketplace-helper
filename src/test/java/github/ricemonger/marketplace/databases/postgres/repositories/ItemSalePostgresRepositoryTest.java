package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class ItemSalePostgresRepositoryTest {
    @SpyBean
    private ItemSalePostgresRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item1);
        entity1.setSoldAt(LocalDateTime.now());

        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item2);
        entity2.setSoldAt(LocalDateTime.now().minusDays(29));

        ItemEntity item3 = new ItemEntity();
        item3.setItemId("item3");
        ItemSaleEntity entity3 = new ItemSaleEntity();
        entity3.setItem(item3);
        entity3.setSoldAt(LocalDateTime.now().minusDays(30));

        repository.save(entity1);
        repository.save(entity2);
        repository.save(entity3);

        List<ItemSaleEntity> result = repository.findAllLastMonthSales();
        for (ItemSaleEntity entity : result) {
            assertNotEquals(entity3.getItem().getItemId(), entity.getItem().getItemId());
        }

        assertEquals(2, result.size());
    }
}