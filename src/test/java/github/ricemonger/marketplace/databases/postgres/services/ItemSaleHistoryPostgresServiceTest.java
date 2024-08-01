package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemSaleHistoryPostgresServiceTest {
    @Autowired
    private ItemSaleHistoryPostgresService itemSaleHistoryService;
    @Autowired
    private ItemSaleHistoryPostgresRepository itemSaleHistoryRepository;

    @BeforeEach
    public void setUp() {
        itemSaleHistoryRepository.deleteAll();
    }

    @Test
    public void saveAll_should_create_new_item_sale_history_in_db_if_doesnt_exist() {
        itemSaleHistoryService.saveAll(List.of(
                createItemSaleHistory("1"),
                createItemSaleHistory("2")
        ));

        assertEquals(2, itemSaleHistoryRepository.count());
    }

    @Test
    public void saveAll_should_update_existing_item_sale_history_in_db() {

        ItemSaleHistory history1 = new ItemSaleHistory();
        history1.setItemId("1");
        history1.setDaySales(999);

        itemSaleHistoryRepository.save(new ItemSaleHistoryEntity(history1));

        ItemSaleHistory history2 = new ItemSaleHistory();
        history2.setItemId("1");
        history2.setDaySales(1000);

        itemSaleHistoryService.saveAll(List.of(
                history2
        ));

        assertEquals(1, itemSaleHistoryRepository.count());
        assertEquals(1000, itemSaleHistoryRepository.findById("1").get().getDaySales());
    }

    private ItemSaleHistory createItemSaleHistory(String id) {
        ItemSaleHistory history = new ItemSaleHistory();
        history.setItemId(id);
        return history;
    }
}