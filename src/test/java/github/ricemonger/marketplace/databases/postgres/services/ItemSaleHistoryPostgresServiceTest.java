package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleHistoryEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemHistoryFields;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

        ItemHistoryFields history1 = new ItemHistoryFields();
        history1.setItemId("1");
        history1.setDaySales(999);

        itemSaleHistoryRepository.save(new ItemSaleHistoryEntity(history1));

        ItemHistoryFields history2 = new ItemHistoryFields();
        history2.setItemId("1");
        history2.setDaySales(1000);

        itemSaleHistoryService.saveAll(List.of(
                history2
        ));

        assertEquals(1, itemSaleHistoryRepository.count());
        assertEquals(1000, itemSaleHistoryRepository.findById("1").get().getTodaySales());
    }

    private ItemHistoryFields createItemSaleHistory(String id) {
        ItemHistoryFields history = new ItemHistoryFields();
        history.setItemId(id);
        return history;
    }
}