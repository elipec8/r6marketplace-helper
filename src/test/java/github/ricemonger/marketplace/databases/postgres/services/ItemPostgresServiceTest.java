package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemPostgresServiceTest {
    @Autowired
    private ItemPostgresService itemService;
    @Autowired
    private ItemPostgresRepository itemRepository;

    @BeforeEach
    public void setUp() {
        itemRepository.deleteAll();
    }

    @Test
    public void saveAll_should_create_new_item_in_db_if_doesnt_exist() {

        List<Item> itemMainFields = List.of(
                createItem("1"),
                createItem("2")
        );

        itemService.saveAll(itemMainFields);

        assertEquals(2, itemRepository.count());
    }

    @Test
    public void saveAll_should_update_existing_item_in_db() {

        ItemEntity item1 = new ItemEntity();
        item1.setItemId("1");
        item1.setName("old name");

        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setItemId("1");
        item2.setName("new name");

        itemService.saveAll(List.of(item2));

        assertEquals(1, itemRepository.count());
        assertEquals("new name", itemRepository.findById("1").get().getName());
    }

    @Test
    public void findById_should_return_item_by_id() {
        ItemEntity item = new ItemEntity();
        item.setItemId("1");
        item.setName("name");
        itemRepository.save(item);

        assertEquals("name", itemService.findById("1").getName());
    }

    @Test
    public void findById_should_throw_exception_if_item_doesnt_exist() {
        assertThrows(ItemDoesntExistException.class, () -> itemService.findById("1"));
    }

    @Test
    public void findAll_should_return_all_items() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId("1");
        item1.setName("name1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("2");
        item2.setName("name2");
        itemRepository.save(item1);
        itemRepository.save(item2);

        List<String> result = itemService.findAll().stream().map(Item::getName).toList();

        assertTrue(List.of("name1", "name2").containsAll(result) && result.containsAll(List.of("name1", "name2")));
    }

    private Item createItem(String itemId) {
        Item item = new Item();
        item.setItemId(itemId);
        return item;
    }
}