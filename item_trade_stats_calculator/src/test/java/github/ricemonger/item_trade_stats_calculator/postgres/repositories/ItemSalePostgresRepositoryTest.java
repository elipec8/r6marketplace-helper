package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemSaleDtoProjection;
import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemSaleDtoProjectionI;
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
class ItemSalePostgresRepositoryTest {
    @SpyBean
    private ItemSalePostgresRepository itemSalePostgresRepository;

    @SpyBean
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        itemSalePostgresRepository.deleteAll();
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_last_30_days() {
        ItemEntity item1 = itemPostgresRepository.saveAndFlush(new ItemEntity("item1"));
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item1);
        entity1.setSoldAt(LocalDateTime.now());

        ItemEntity item2 = itemPostgresRepository.saveAndFlush(new ItemEntity("item2"));
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item2);
        entity2.setSoldAt(LocalDateTime.now().minusDays(30));

        ItemEntity item3 = itemPostgresRepository.saveAndFlush(new ItemEntity("item3"));
        ItemSaleEntity entity3 = new ItemSaleEntity();
        entity3.setItem(item3);
        entity3.setSoldAt(LocalDateTime.now().minusDays(31));

        itemSalePostgresRepository.save(entity1);
        itemSalePostgresRepository.save(entity2);
        itemSalePostgresRepository.save(entity3);

        List<ItemSaleDtoProjectionI> result = itemSalePostgresRepository.findAllForLastMonth();
        for (ItemSaleDtoProjectionI dto : result) {
            assertNotEquals(entity3.getItem().getItemId(), dto.getItemId());
        }

        assertEquals(2, result.size());
    }
}