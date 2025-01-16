package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemDaySalesUbiStatsProjectionI;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
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
class CustomItemDaySalesUbiStatsPostgresRepositoryTest {
    @SpyBean
    private CustomItemDaySalesUbiStatsPostgresRepository customItemDaySalesUbiStatsPostgresRepository;

    @SpyBean
    private CustomItemPostgresRepository customItemPostgresRepository;

    @BeforeEach
    void setUp() {
        customItemDaySalesUbiStatsPostgresRepository.deleteAll();
        customItemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item1"));
        ItemDaySalesUbiStatsEntity entity1 = new ItemDaySalesUbiStatsEntity();
        entity1.setItem(item1);
        entity1.setDate(LocalDate.now());

        ItemEntity item2 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item2"));
        ItemDaySalesUbiStatsEntity entity2 = new ItemDaySalesUbiStatsEntity();
        entity2.setItem(item2);
        entity2.setDate(LocalDate.now().minusDays(30));

        ItemEntity item3 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item3"));
        ItemDaySalesUbiStatsEntity entity3 = new ItemDaySalesUbiStatsEntity();
        entity3.setItem(item3);
        entity3.setDate(LocalDate.now().minusDays(31));

        customItemDaySalesUbiStatsPostgresRepository.save(entity1);
        customItemDaySalesUbiStatsPostgresRepository.save(entity2);
        customItemDaySalesUbiStatsPostgresRepository.save(entity3);

        List<ItemDaySalesUbiStatsProjectionI> result = customItemDaySalesUbiStatsPostgresRepository.findAllForLastMonth();
        for (ItemDaySalesUbiStatsProjectionI dto : result) {
            assertNotEquals(entity3.getItem().getItemId(), dto.getItemId());
        }

        assertEquals(2, result.size());
    }
}