package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemSale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ItemSalePostgresServiceTest {
    private final static LocalDateTime DATE = LocalDateTime.now();

    @SpyBean
    private ItemSalePostgresService itemSaleService;
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
    public void saveAll_should_create_new_sales_if_doesnt_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);
        Item item3 = createSoldItem("1", LocalDateTime.MAX, 100);

        itemRepository.saveAll(Stream.of(item1, item2, item3).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(3, itemSaleRepository.count());
    }

    @Test
    public void saveAll_should_update_sales_if_already_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 200);
        Item item11 = createSoldItem("1", DATE, 200);

        itemRepository.saveAll(Stream.of(item1, item2).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2));

        itemSaleService.saveAll(List.of(item2, item11));

        assertEquals(2, itemSaleRepository.count());
        assertEquals(200, itemSaleRepository.findById(new ItemSaleEntityId(new ItemEntity(item1, Set.of()), DATE)).get().getPrice());
    }

    @Test
    public void saveAll_should_skip_doesnt_exist() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);
        Item item3 = createSoldItem("3", DATE, 100);

        itemRepository.saveAll(Stream.of(item1).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(1, itemSaleRepository.count());
    }

    @Test
    public void findAll_should_return_mapped_result_from_repository() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);

        when(itemSaleRepository.findAll()).thenReturn(List.of(new ItemSaleEntity(new ItemEntity(item1, Set.of()), DATE, 100),
                new ItemSaleEntity(new ItemEntity(item2, Set.of()), DATE, 100)));

        List<ItemSale> expected = List.of(new ItemSale(item1.getItemId(), DATE, 100), new ItemSale(item2.getItemId(), DATE, 100));
        List<ItemSale> result = itemSaleService.findAll();

        assertEquals(2, result.size());
        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void findAllForLastMonth_should_return_mapped_result_from_repository() {
        Item item1 = createSoldItem("1", DATE, 100);
        Item item2 = createSoldItem("2", DATE, 100);

        when(itemSaleRepository.findAllForLastMonth()).thenReturn(List.of(new ItemSaleEntity(new ItemEntity(item1, Set.of()), DATE, 100),
                new ItemSaleEntity(new ItemEntity(item2, Set.of()), DATE, 100)));

        List<ItemSale> expected = List.of(new ItemSale(item1.getItemId(), DATE, 100), new ItemSale(item2.getItemId(), DATE, 100));
        List<ItemSale> result = itemSaleService.findAllForLastMonth();

        assertEquals(2, result.size());
        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    private Item createSoldItem(String id, LocalDateTime date, int price) {
        Item item = new Item();
        item.setItemId(id);
        item.setLastSoldAt(date);
        item.setLastSoldPrice(price);
        return item;
    }
}