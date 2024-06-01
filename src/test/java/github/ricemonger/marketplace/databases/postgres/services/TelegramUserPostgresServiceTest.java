package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.TelegramUserPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.telegramBot.executors.InputState;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserPostgresServiceTest {

    @MockBean
    private TelegramUserPostgresRepository telegramUserPostgresRepository;

    @MockBean
    private TelegramUserInputPostgresRepository telegramUserInputPostgresRepository;

    @MockBean
    private TelegramUserPostgresMapper mapper;

    @Autowired
    private TelegramUserPostgresService telegramUserPostgresService;

    @Test
    public void saveUser_should_map_and_save() {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        when(mapper.mapTelegramUserEntity(any())).thenReturn(telegramUserEntity);

        TelegramUser user = new TelegramUser();

        telegramUserPostgresService.saveUser(user);

        verify(mapper).mapTelegramUserEntity(user);
        verify(telegramUserPostgresRepository).save(telegramUserEntity);
    }

    @Test
    public void userExistsById_should_return_be_same_to_repository_result() {
        when(telegramUserPostgresRepository.existsById("1")).thenReturn(true);
        when(telegramUserPostgresRepository.existsById("2")).thenReturn(false);

        assertTrue(telegramUserPostgresService.userExistsById("1"));
        assertFalse(telegramUserPostgresService.userExistsById("2"));
    }

    @Test
    public void findUserById_should_map_and_return() {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.of(telegramUserEntity));

        TelegramUser user = new TelegramUser();
        when(mapper.mapTelegramUser(telegramUserEntity)).thenReturn(user);

        assertEquals(user, telegramUserPostgresService.findUserById("1"));
    }

    @Test
    public void findUserById_should_throw_if_not_found() {
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserPostgresService.findUserById("1"));
    }

    @Test
    public void findAllUsers_should_map_and_return() {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();
        when(telegramUserPostgresRepository.findAll()).thenReturn(java.util.List.of(telegramUserEntity));

        TelegramUser user = new TelegramUser();
        when(mapper.mapTelegramUsers(java.util.List.of(telegramUserEntity))).thenReturn(java.util.List.of(user));

        assertEquals(java.util.List.of(user), telegramUserPostgresService.findAllUsers());
    }

    @Test
    public void findAllUsers_should_return_empty_list_if_empty() {
        when(telegramUserPostgresRepository.findAll()).thenReturn(java.util.List.of());

        assertEquals(java.util.List.of(), telegramUserPostgresService.findAllUsers());
    }

    @Test
    public void saveInput_should_map_and_save() {
        when(mapper.mapTelegramUserInputEntity(any())).thenReturn(null);

        TelegramUserInput input = new TelegramUserInput();
        input.setChatId("1");

        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.of(new TelegramUserEntity()));

        telegramUserPostgresService.saveInput(input);

        verify(mapper).mapTelegramUserInputEntity(input);
        verify(telegramUserInputPostgresRepository).save(null);
    }

    @Test
    public void saveInput_should_throw_if_user_doesnt_exist() {
        TelegramUserInput input = new TelegramUserInput("1", InputState.BASE, "");

        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserPostgresService.saveInput(input));
    }

    @Test
    public void deleteAllInputsByChatId_should_call_repository() {
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.of(new TelegramUserEntity()));

        telegramUserPostgresService.deleteAllInputsByChatId("1");

        verify(telegramUserInputPostgresRepository).deleteAllByChatId("1");
    }

    @Test
    public void deleteAllInputsByChatId_should_throw_if_user_doesnt_exist() {
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserPostgresService.deleteAllInputsByChatId("1"));
    }

    @Test
    public void findInputById_should_map_and_return() {
        TelegramUserInput input = new TelegramUserInput();
        TelegramUserInputEntity entity = new TelegramUserInputEntity();
        when(telegramUserInputPostgresRepository.findById(any())).thenReturn(Optional.of(entity));
        when(mapper.mapTelegramUserInput(any())).thenReturn(input);
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.of(new TelegramUserEntity()));

        assertEquals(input, telegramUserPostgresService.findInputById("1", null));
    }

    @Test
    public void findInputById_should_throw_if_no_input() {
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.of(new TelegramUserEntity()));

        when(telegramUserInputPostgresRepository.findById(any())).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserInputDoesntExistException.class, () -> telegramUserPostgresService.findInputById("1", InputState.BASE));
    }

    @Test
    public void findInputById_should_throw_if_user_doesnt_exist() {
        when(telegramUserPostgresRepository.findById("1")).thenReturn(java.util.Optional.empty());

        assertThrows(TelegramUserDoesntExistException.class, () -> telegramUserPostgresService.findInputById("1", InputState.BASE));
    }
}