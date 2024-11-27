package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class ItemDaySalesUbiStatsPostgresRepositoryTest {
    @SpyBean
    private ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsPostgresRepository;

    @SpyBean
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        itemDaySalesUbiStatsPostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = itemPostgresRepository.saveAndFlush(new ItemEntity("item1"));
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(item1);
        entity1.setDate(LocalDate.now());

        ItemEntity item2 = itemPostgresRepository.saveAndFlush(new ItemEntity("item2"));
        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(item2);
        entity2.setDate(LocalDate.now().minusDays(30));

        ItemEntity item3 = itemPostgresRepository.saveAndFlush(new ItemEntity("item3"));
        ItemDaySalesUbiStatsEntity entity3 = new ItemDaySalesUbiStatsEntity();
        entity3.setItem(item3);
        entity3.setDate(LocalDate.now().minusDays(31));

        itemDaySalesUbiStatsPostgresRepository.save(entity1);
        itemDaySalesUbiStatsPostgresRepository.save(entity2);
        itemDaySalesUbiStatsPostgresRepository.save(entity3);

        List<ItemDaySalesUbiStatsEntity> result = itemDaySalesUbiStatsPostgresRepository.findAllForLastMonth();
        for (ItemDaySalesUbiStatsEntity entity : result) {
            assertNotEquals(entity3.getItem().getItemId(), entity.getItem().getItemId());
        }

        assertEquals(2, result.size());
    }
}