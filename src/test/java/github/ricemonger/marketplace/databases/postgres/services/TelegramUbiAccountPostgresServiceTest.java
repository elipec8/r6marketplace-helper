package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserPostgresRepository;
import github.ricemonger.utils.dtos.UbiAccount;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUbiAccountPostgresServiceTest {

    @MockBean
    private UbiUserPostgresRepository repository;

    @Autowired
    private TelegramUserUbiAccountPostgresService service;

    @Test
    public void create_should_handle_to_repository(){
        TelegramLinkedUbiUserEntity entity = new TelegramLinkedUbiUserEntity();
        entity.setChatId("chatId");

        UbiAccount user = entity.toUbiUser();

        service.save(user);

        verify(repository).save(argThat(argument -> argument.getChatId().equals("chatId")));
    }

    @Test
    public void deleteByChatId_should_handle_to_repository(){
        service.deleteByChatId("chatId", "email");

        verify(repository).deleteById(new TelegramLinkedUbiUserEntityId("chatId", "email"));
    }

    @Test
    public void deleteAllByChatId_should_handle_to_repository(){
        service.deleteAllByChatId("chatId");

        verify(repository).deleteAllByChatId("chatId");
    }

    @Test
    public void findByChatId_should_throw_exception_when_user_doesnt_exist() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UbiUserDoesntExistException.class, () -> service.findByChatId("chatId", "email"));
    }

    @Test
    public void findByChatId_should_handle_to_repository(){
        TelegramLinkedUbiUserEntity entity = new TelegramLinkedUbiUserEntity();
        entity.setChatId("chatId");

        when(repository.findById(any())).thenReturn(Optional.of(entity));

        service.findByChatId("chatId", "email");

        verify(repository).findById(new TelegramLinkedUbiUserEntityId("chatId", "email"));

        assertEquals(entity.toUbiUser(), service.findByChatId("chatId", "email"));
    }

    @Test
    public void findAllByChatId_should_handle_to_repository(){
        List<TelegramLinkedUbiUserEntity> entities = new ArrayList<>();
        TelegramLinkedUbiUserEntity entity = new TelegramLinkedUbiUserEntity();
        entity.setChatId("chatId");
        entities.add(entity);

        when(repository.findAllByChatId("chatId")).thenReturn(entities);

        service.findAllByChatId("chatId");

        verify(repository).findAllByChatId("chatId");

        assertEquals(service.findAllByChatId("chatId"), List.of(entity.toUbiUser()));
    }

    @Test
    public void findAll_should_handle_to_repository(){
        List<TelegramLinkedUbiUserEntity> entities = new ArrayList<>();
        TelegramLinkedUbiUserEntity entity = new TelegramLinkedUbiUserEntity();
        entity.setChatId("chatId");
        entities.add(entity);

        when(repository.findAll()).thenReturn(entities);

        service.findAll();

        verify(repository).findAll();

        assertEquals(service.findAll(), List.of(entity.toUbiUser()));
    }
}
