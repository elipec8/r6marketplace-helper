package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ItemMainFieldsPostgresRepositoryTest {

    @SpyBean
    private ItemMainFieldsPostgresRepository itemPostgresRepository;

    @BeforeEach
    void setUp() {
        itemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllItemIds_should_return_all_item_ids() {
        ItemMainFieldsEntity item1 = new ItemMainFieldsEntity();
        item1.setItemId("itemId1");
        ItemMainFieldsEntity item2 = new ItemMainFieldsEntity();
        item2.setItemId("itemId4");
        ItemMainFieldsEntity item3 = new ItemMainFieldsEntity();
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