package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemSalePostgresServiceTest {
    @MockBean
    private ItemSalePostgresRepository repository;

    @Autowired
    private ItemSalePostgresService service;

    @Test
    public void saveAll_should_handle_to_repository() {
        Item item1 = new Item();
        item1.setItemId("1");
        Item item2 = new Item();
        item2.setItemId("2");

        Collection<Item> items = List.of(item1, item2);

        ItemSale sale1 = new ItemSale();
        sale1.setItemId("1");
        ItemSale sale2 = new ItemSale();
        sale2.setItemId("2");
        Collection<ItemSale> itemSales = List.of(sale1, sale2);

        service.saveAll(items);

        verify(repository).saveAll(argThat((ArgumentMatcher<Iterable<? extends ItemSaleEntity>>) argument -> {
            Collection<ItemSale> saleArg = new ArrayList<>();
            argument.forEach(entity -> saleArg.add(entity.toItemSale()));

            return saleArg.containsAll(itemSales) && itemSales.containsAll(saleArg);
        }));
    }

    @Test
    public void findAll_should_handle_to_repository() {
        Item item1 = new Item();
        item1.setItemId("1");
        Item item2 = new Item();
        item2.setItemId("2");

        List<Item> expectedItems = List.of(item1, item2);

        ItemSale sale1 = new ItemSale();
        sale1.setItemId("1");
        ItemSale sale2 = new ItemSale();
        sale2.setItemId("2");
        Collection<ItemSale> expectedSales = List.of(sale1, sale2);

        when(repository.findAll()).thenReturn(expectedItems.stream().map(ItemSaleEntity::new).toList());

        Collection<ItemSale> result = service.findAll();

        assertTrue(result.containsAll(expectedSales) && expectedSales.containsAll(result));
    }
}