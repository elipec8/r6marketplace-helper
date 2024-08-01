package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.dtos.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ItemSalePostgresServiceTest {
    private final static Date DATE = new Date();

    @Autowired
    private ItemSalePostgresService itemSaleService;
    @Autowired
    private ItemSalePostgresRepository itemSaleRepository;

    @BeforeEach
    void setUp() {
        itemSaleRepository.deleteAll();
    }

    @Test
    public void saveAll_should_create_new_sales_if_doesnt_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);
        Item item3 = createSoldItem("1", new Date(DATE.getTime() + 1), 100);

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(3, itemSaleRepository.count());
    }

    @Test
    public void saveAll_should_update_sales_if_already_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("1", DATE, 200);

        itemSaleService.saveAll(List.of(item1, item2));

        assertEquals(1, itemSaleRepository.count());
        assertEquals(itemSaleRepository.findAll().get(0).getPrice(), 200);
    }

    private Item createSoldItem(String id, Date date, int price) {
        Item item = new Item();
        item.setItemId(id);
        item.setLastSoldAt(date);
        item.setLastSoldPrice(price);
        return item;
    }
}