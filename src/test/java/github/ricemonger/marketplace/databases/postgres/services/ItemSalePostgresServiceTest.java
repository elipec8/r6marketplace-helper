package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ItemSalePostgresServiceTest {
    private final static Date DATE = new Date();

    @Autowired
    private ItemSalePostgresService itemSaleService;
    @Autowired
    private ItemSalePostgresRepository itemSaleRepository;
    @Autowired
    private ItemPostgresRepository itemRepository;

    @BeforeEach
    void setUp() {
        itemSaleRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    public void saveAll_should_create_new_sales_if_doesnt_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);
        Item item3 = createSoldItem("1", new Date(DATE.getTime() + 1), 100);

        itemRepository.saveAll(Stream.of(item1, item2, item3).map(ItemEntity::new).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(3, itemSaleRepository.count());
    }

    @Test
    public void saveAll_should_update_sales_if_already_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 200);
        Item item11 = createSoldItem("1", DATE, 200);

        itemRepository.saveAll(Stream.of(item1, item2).map(ItemEntity::new).toList());

        itemSaleService.saveAll(List.of(item1, item2));

        itemSaleService.saveAll(List.of(item2, item11));

        assertEquals(2, itemSaleRepository.count());
        assertEquals(itemSaleRepository.findById(new ItemSaleEntityId(new ItemEntity(item1),DATE)).get().getPrice(), 200);
    }

    @Test
    public void saveAll_should_not_save_or_throw_if_item_doesnt_exist(){
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);
        Item item3 = createSoldItem("3", new Date(DATE.getTime() + 1), 100);

        itemRepository.saveAll(Stream.of(item1).map(ItemEntity::new).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(1, itemSaleRepository.count());
    }

    @Test
    public void findAll_should_return_all_sales() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);

        itemRepository.saveAll(Stream.of(item1, item2).map(ItemEntity::new).toList());

        itemSaleService.saveAll(List.of(item1, item2));

        List<ItemSale> sales = itemSaleService.findAll();

        System.out.println(DATE);
        assertEquals(2, sales.size());

        System.out.println(sales.stream().map(ItemSale::toString).toList());

        assertTrue(sales.stream().anyMatch(s -> s.getItemId().equals("1") && s.getPrice() == 100 && s.getSoldAt().getTime()==(DATE.getTime())));
        assertTrue(sales.stream().anyMatch(s -> s.getItemId().equals("2") && s.getPrice() == 100 && s.getSoldAt().getTime()==(DATE.getTime())));
    }

    private Item createSoldItem(String id, Date date, int price) {
        Item item = new Item();
        item.setItemId(id);
        item.setLastSoldAt(date);
        item.setLastSoldPrice(price);
        return item;
    }
}