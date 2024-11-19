package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemPostgresRepositoryTest {

    @SpyBean
    private ItemPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllItemIds_should_return_all_item_ids() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("itemId1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("itemId4");
        ItemEntity item3 = new ItemEntity();
        item3.setItemId("itemId7");
        itemPostgresRepository.save(item1);
        itemPostgresRepository.save(item2);
        itemPostgresRepository.save(item3);

        Set<String> itemIds = itemPostgresRepository.findAllItemIds();

        assertEquals(3, itemIds.size());
        assertTrue(itemIds.contains("itemId1"));
        assertTrue(itemIds.contains("itemId4"));
        assertTrue(itemIds.contains("itemId7"));
    }

}