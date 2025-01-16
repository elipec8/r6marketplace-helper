package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class CustomItemPostgresRepositoryTest {
    @Autowired
    private CustomItemPostgresRepository customItemPostgresRepository;

    @BeforeEach
    public void setUp() {
        customItemPostgresRepository.deleteAll();
    }

    @Test
    public void findAllItemIds_should_return_ids_of_all_items() {
        customItemPostgresRepository.save(new ItemEntity("itemId1"));
        customItemPostgresRepository.save(new ItemEntity("itemId2"));

        List<String> itemIds = customItemPostgresRepository.findAllItemIds();

        assertEquals(2, itemIds.size());
        assertTrue(itemIds.contains("itemId1"));
        assertTrue(itemIds.contains("itemId2"));
    }
}