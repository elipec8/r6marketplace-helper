package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import github.ricemonger.utils.DTOs.common.ItemMinSellPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
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

    @Test
    public void updateAllItemsMinSellPrice_should_update_all_items_min_sell_price() {
        ItemMainFieldsEntity item1 = new ItemMainFieldsEntity();
        item1.setItemId("itemId1");
        item1.setMinSellPrice(100);
        ItemMainFieldsEntity item2 = new ItemMainFieldsEntity();
        item2.setItemId("itemId2");
        item2.setMinSellPrice(200);
        ItemMainFieldsEntity item3 = new ItemMainFieldsEntity();
        item3.setItemId("itemId3");
        item3.setMinSellPrice(300);
        itemPostgresRepository.save(item1);
        itemPostgresRepository.save(item2);
        itemPostgresRepository.save(item3);

        List<ItemMinSellPrice> minSellPrices = List.of(
                new ItemMinSellPrice("itemId1", 150),
                new ItemMinSellPrice("itemId2", 250),
                new ItemMinSellPrice("itemId3", 350)
        );

        itemPostgresRepository.updateAllItemsMinSellPrice(minSellPrices);

        assertEquals(150, itemPostgresRepository.findById("itemId1").get().getMinSellPrice());
        assertEquals(250, itemPostgresRepository.findById("itemId2").get().getMinSellPrice());
        assertEquals(350, itemPostgresRepository.findById("itemId3").get().getMinSellPrice());
    }
}