package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.exceptions.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ItemPostgresServiceTest {

    @MockBean
    private ItemPostgresRepository repository;

    @Autowired
    private ItemPostgresService service;

    @Test
    public void saveAll_should_handle_to_repository() {
        Item item1 = new Item();
        item1.setItemId("1");
        Item item2 = new Item();
        item2.setItemId("2");

        List<Item> items = List.of(item1, item2);

        service.saveAll(items);

        verify(repository).saveAll(argThat((ArgumentMatcher<Iterable<? extends ItemEntity>>) argument -> {
            Collection<ItemEntity> argEntities = new ArrayList<>();
            argument.forEach(argEntities::add);

            Collection<Item> argItems = argEntities.stream().map(ItemEntity::toItem).toList();

            return argItems.containsAll(items) && items.containsAll(argItems);
        }));
    }

    @Test
    public void findById_should_handle_to_repository() {
        Item item = new Item();
        item.setItemId("1");

        when(repository.findById("1")).thenReturn(java.util.Optional.of(new ItemEntity(item)));

        Item result = service.findById("1");

        verify(repository).findById("1");

        assertEquals(result, item);
    }

    @Test
    public void findById_should_throw_if_not_found(){
        when(repository.findById("1")).thenReturn(java.util.Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    public void findAll_should_handle_to_repository() {
        Item item1 = new Item();
        item1.setItemId("1");
        Item item2 = new Item();
        item2.setItemId("2");

        List<Item> expected = List.of(item1, item2);

        when(repository.findAll()).thenReturn(expected.stream().map(ItemEntity::new).toList());

        Collection<Item> result = service.findAll();

        verify(repository).findAll();

        assertTrue(result.containsAll(expected) && expected.containsAll(result));
    }
}