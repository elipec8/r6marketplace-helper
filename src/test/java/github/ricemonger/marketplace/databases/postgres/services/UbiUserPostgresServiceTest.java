package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.UbiUserEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.UbiUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.UbiUserPostgresRepository;
import github.ricemonger.utils.dtos.UbiUser;
import github.ricemonger.utils.exceptions.UbiUserDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UbiUserPostgresServiceTest {

    @MockBean
    private UbiUserPostgresRepository ubiUserRepository;

    @MockBean
    private UbiUserPostgresMapper mapper;

    @Autowired
    private UbiUserPostgresService ubiUserPostgresService;

    @Test
    public void save_should_map_and_and_repository() {
        UbiUserEntity entity = new UbiUserEntity();
        UbiUser ubiUser = new UbiUser();

        when(mapper.toUbiUserEntity(any())).thenReturn(entity);

        ubiUserPostgresService.save(ubiUser);

        verify(ubiUserRepository).save(entity);
    }

    @Test
    public void deleteById_should_call_repository() {
        ubiUserPostgresService.deleteById("chatId", "email");

        verify(ubiUserRepository).deleteById(new UbiUserEntityId("chatId", "email"));
    }

    @Test
    public void deleteAllByChatId_should_call_repository() {
        ubiUserPostgresService.deleteAllByChatId("chatId");

        verify(ubiUserRepository).deleteAllByChatId("chatId");
    }

    @Test
    public void getOwnedItemsIds_should_return_owned_items_from_repository() {
        UbiUserEntity entity = new UbiUserEntity();
        UbiUser ubiUser = new UbiUser();
        ubiUser.setOwnedItemsIds(List.of("1", "2"));

        when(ubiUserRepository.findById(new UbiUserEntityId("chatId","email"))).thenReturn(java.util.Optional.of(entity));
        when(mapper.toUbiUser(entity)).thenReturn(ubiUser);

        assertEquals(List.of("1", "2"), ubiUserPostgresService.getOwnedItemsIds("chatId", "email"));
    }

    @Test
    public void getOwnedItemsIds_should_throw_if_ubi_user_doesnt_exist(){
        when(ubiUserRepository.findById(new UbiUserEntityId("chatId","email"))).thenReturn(java.util.Optional.empty());

        assertThrows(UbiUserDoesntExistException.class, () -> ubiUserPostgresService.getOwnedItemsIds("chatId", "email"));
    }

    @Test
    public void findById_should_return_user_from_repository() {
        UbiUserEntity entity = new UbiUserEntity();
        UbiUser ubiUser = new UbiUser();

        when(ubiUserRepository.findById(new UbiUserEntityId("chatId","email"))).thenReturn(java.util.Optional.of(entity));
        when(mapper.toUbiUser(entity)).thenReturn(ubiUser);

        UbiUser result = ubiUserPostgresService.findById("chatId", "email");

        assertEquals(ubiUser, result);
    }

    @Test
    public void findById_should_throw_if_ubi_user_doesnt_exist(){
        when(ubiUserRepository.findById(new UbiUserEntityId("chatId","email"))).thenReturn(java.util.Optional.empty());

        assertThrows(UbiUserDoesntExistException.class, () -> ubiUserPostgresService.findById("chatId", "email"));
    }

    @Test
    public void findAllByChatId_should_return_users_from_repository() {
        UbiUserEntity entity = new UbiUserEntity();
        UbiUser ubiUser = new UbiUser();

        List<UbiUserEntity> entities = List.of(entity);

        when(ubiUserRepository.findAllByChatId("chatId")).thenReturn(List.of(entity));
        when(mapper.toUbiUsers(entities)).thenReturn(List.of(ubiUser));

        assertEquals(List.of(ubiUser), ubiUserPostgresService.findAllByChatId("chatId"));
    }

    @Test
    public void findAll_should_return_users_from_repository(){
        UbiUserEntity entity = new UbiUserEntity();
        UbiUser ubiUser = new UbiUser();

        List<UbiUserEntity> entities = List.of(entity);

        when(ubiUserRepository.findAll()).thenReturn(List.of(entity));
        when(mapper.toUbiUsers(entities)).thenReturn(List.of(ubiUser));

        assertEquals(List.of(ubiUser), ubiUserPostgresService.findAll());
    }
}
