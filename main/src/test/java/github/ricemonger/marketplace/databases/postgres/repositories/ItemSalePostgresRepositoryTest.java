package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
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
    private ItemSalePostgresRepository itemSaleRepository;
    @SpyBean
    private ItemPostgresRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemSaleRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    public void findAllLastMonthSales_should_return_only_entities_from_For_last_30_days() {
        ItemEntity item1 = itemRepository.saveAndFlush(new ItemEntity("item1"));
        ItemSaleEntity entity1 = new ItemSaleEntity();
        entity1.setItem(item1);
        entity1.setSoldAt(LocalDateTime.now());

        ItemEntity item2 = itemRepository.saveAndFlush(new ItemEntity("item2"));
        ItemSaleEntity entity2 = new ItemSaleEntity();
        entity2.setItem(item2);
        entity2.setSoldAt(LocalDateTime.now().minusDays(30));

        ItemEntity item3 = itemRepository.saveAndFlush(new ItemEntity("item3"));
        ItemSaleEntity entity3 = new ItemSaleEntity();
        entity3.setItem(item3);
        entity3.setSoldAt(LocalDateTime.now().minusDays(31));

        itemSaleRepository.save(entity1);
        itemSaleRepository.save(entity2);
        itemSaleRepository.save(entity3);

        List<ItemSaleEntity> result = itemSaleRepository.findAllForLastMonth();
        for (ItemSaleEntity entity : result) {
            assertNotEquals(entity3.getItem().getItemId(), entity.getItem().getItemId());
        }

        assertEquals(2, result.size());
    }
}