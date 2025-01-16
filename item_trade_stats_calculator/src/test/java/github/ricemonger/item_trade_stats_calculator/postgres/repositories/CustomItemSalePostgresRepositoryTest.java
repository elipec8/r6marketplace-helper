package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemSaleProjectionI;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemSaleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Transactional
class CustomItemSalePostgresRepositoryTest {
    @SpyBean
    private CustomItemSalePostgresRepository customItemSalePostgresRepository;

    @SpyBean
    private CustomItemPostgresRepository customItemPostgresRepository;

    @BeforeEach
    void setUp() {
        customItemSalePostgresRepository.deleteAll();
        customItemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item1"));
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item1);
        entity1.setSoldAt(LocalDateTime.now());

        ItemEntity item2 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item2"));
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item2);
        entity2.setSoldAt(LocalDateTime.now().minusDays(30));

        ItemEntity item3 = customItemPostgresRepository.saveAndFlush(new ItemEntity("item3"));
        ItemSaleEntity entity3 = new ItemSaleEntity();
        entity3.setItem(item3);
        entity3.setSoldAt(LocalDateTime.now().minusDays(31));

        customItemSalePostgresRepository.save(entity1);
        customItemSalePostgresRepository.save(entity2);
        customItemSalePostgresRepository.save(entity3);

        List<ItemSaleProjectionI> result = customItemSalePostgresRepository.findAllForLastMonth();
        for (ItemSaleProjectionI dto : result) {
            assertNotEquals(entity3.getItem().getItemId(), dto.getItemId());
        }

        assertEquals(2, result.size());
    }
}