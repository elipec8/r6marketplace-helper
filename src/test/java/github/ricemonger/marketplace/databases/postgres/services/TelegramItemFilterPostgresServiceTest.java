package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramItemFilterPostgresServiceTest {
    @MockBean
    private ItemFilterPostgresRepository repository;
    @Autowired
    private TelegramItemFilterPostgresService service;

    @Test
    public void save_should_handle_to_repository() {
        ItemFilter filter = new ItemFilter();
        filter.setChatId("chatId");
        filter.setName("name");

        service.save(filter);

        verify(repository).save(any());
    }

    @Test
    public void findById_should_handle_to_repository() {
        String chatId = "chatId";
        String name = "name";

        ItemFilterEntity entity = new ItemFilterEntity();
        entity.setChatId(chatId);
        entity.setName(name);

        Optional<ItemFilterEntity> result = Optional.of(entity);

        when(repository.findById(new ItemFilterEntityId(chatId, name))).thenReturn(result);

        service.findById(chatId, name);

        verify(repository).findById(new ItemFilterEntityId(chatId, name));

        assertEquals(entity.toItemFilter(), service.findById(chatId, name));
    }

    @Test
    public void findById_should_throw_if_doesnt_exist() {
        String chatId = "chatId";
        String name = "name";

        when(repository.findById(new ItemFilterEntityId(chatId, name))).thenReturn(Optional.empty());

        assertThrows(ItemFilterDoesntExistException.class, () -> service.findById(chatId, name));
    }

    @Test
    public void deleteById_should_handle_to_repository() {
        String chatId = "chatId";
        String name = "name";

        service.deleteById(chatId, name);

        verify(repository).deleteById(new ItemFilterEntityId(chatId, name));
    }

    @Test
    public void findAllByUserId_should_handle_to_repository() {
        Long chatId = "chatId";

        List<ItemFilterEntity> entities = new ArrayList<>();
        entities.add(new ItemFilterEntity());
        entities.add(new ItemFilterEntity());

        List<ItemFilter> filters = entities.stream().map(ItemFilterEntity::toItemFilter).toList();

        when(repository.findAllByChatId(chatId)).thenReturn(entities);

        Collection<ItemFilter> result = service.findAllByUserId(chatId);

        verify(repository).findAllByChatId(chatId);

        assertTrue(result.size() == 2 && result.containsAll(filters) && filters.containsAll(result));
    }
}