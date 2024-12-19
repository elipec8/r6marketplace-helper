package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.DTOs.items.ItemSaleEntityDTO;
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
    private final static LocalDateTime DATE = LocalDateTime.now().withNano(0);

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
        ItemEntityDTO item1 = createSoldItem("1", DATE, 100);
        ItemEntityDTO item2 = createSoldItem("2", DATE, 100);
        ItemEntityDTO item3 = createSoldItem("1", LocalDateTime.MAX, 100);

        itemRepository.saveAll(Stream.of(item1, item2, item3).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(3, itemSaleRepository.count());
    }

    @Test
    public void saveAll_should_update_sales_if_already_exist() {
        ItemEntityDTO item1 = createSoldItem("1", DATE, 100);
        ItemEntityDTO item2 = createSoldItem("2", DATE, 200);
        ItemEntityDTO item1Updated = createSoldItem("1", DATE, 200);

        itemRepository.saveAllAndFlush(Stream.of(item1, item2).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2));

        itemSaleService.saveAll(List.of(item2, item1Updated));

        assertEquals(2, itemSaleRepository.count());
        assertEquals(200, itemSaleRepository.findById(new ItemSaleEntityId(new ItemEntity(item1, Set.of()), DATE)).get().getPrice());
    }

    @Test
    public void saveAll_should_skip_doesnt_exist() {
        ItemEntityDTO item1 = createSoldItem("1", DATE, 100);
        ItemEntityDTO item2 = createSoldItem("2", DATE, 100);
        ItemEntityDTO item3 = createSoldItem("3", DATE, 100);

        itemRepository.saveAll(Stream.of(item1).map(item -> new ItemEntity(item, Set.of())).toList());

        itemSaleService.saveAll(List.of(item1, item2, item3));

        assertEquals(1, itemSaleRepository.count());
    }

    @Test
    public void findAll_should_return_mapped_result_from_repository() {
        ItemEntityDTO item1 = createSoldItem("1", DATE, 100);
        ItemEntityDTO item2 = createSoldItem("2", DATE, 100);

        when(itemSaleRepository.findAll()).thenReturn(List.of(new ItemSaleEntity(new ItemEntity(item1, Set.of()), DATE, 100),
                new ItemSaleEntity(new ItemEntity(item2, Set.of()), DATE, 100)));

        List<ItemSaleEntityDTO> expected = List.of(new ItemSaleEntityDTO(item1.getItemId(), DATE, 100), new ItemSaleEntityDTO(item2.getItemId(), DATE, 100));
        List<ItemSaleEntityDTO> result = itemSaleService.findAll();

        assertEquals(2, result.size());
        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    @Test
    public void findAllForLastMonth_should_return_mapped_result_from_repository() {
        ItemEntityDTO item1 = createSoldItem("1", DATE, 100);
        ItemEntityDTO item2 = createSoldItem("2", DATE, 100);

        when(itemSaleRepository.findAllForLastMonth()).thenReturn(List.of(new ItemSaleEntity(new ItemEntity(item1, Set.of()), DATE, 100),
                new ItemSaleEntity(new ItemEntity(item2, Set.of()), DATE, 100)));

        List<ItemSaleEntityDTO> expected = List.of(new ItemSaleEntityDTO(item1.getItemId(), DATE, 100), new ItemSaleEntityDTO(item2.getItemId(), DATE, 100));
        List<ItemSaleEntityDTO> result = itemSaleService.findAllForLastMonth();

        assertEquals(2, result.size());
        assertTrue(expected.containsAll(result) && result.containsAll(expected));
    }

    private ItemEntityDTO createSoldItem(String id, LocalDateTime date, int price) {
        ItemEntityDTO item = new ItemEntityDTO();
        item.setItemId(id);
        item.setLastSoldAt(date);
        item.setLastSoldPrice(price);
        return item;
    }
}