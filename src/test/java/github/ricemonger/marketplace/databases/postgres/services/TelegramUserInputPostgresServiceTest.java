package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserInputPostgresRepository;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.dtos.TelegramUserInput;
import github.ricemonger.utils.exceptions.TelegramUserInputDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserInputPostgresServiceTest {
    @MockBean
    private TelegramUserInputPostgresRepository repository;

    @Autowired
    private TelegramUserInputPostgresService service;

    @Test
    public void save_should_handle_to_repository() {
        TelegramUserInput input = new TelegramUserInput("chatId", InputState.BASE, "value");
        service.save(input.getChatId(), input.getInputState(), input.getValue());

        verify(repository).save(argThat(argument -> argument.getChatId().equals(input.getChatId()) &&
                                                    argument.getInputState().equals(input.getInputState()) &&
                                                    argument.getValue().equals(input.getValue())));
    }

    @Test
    public void deleteAllByChatId_should_handle_to_repository() {
        service.deleteAllByChatId("chatId");

        verify(repository).deleteAllByTelegramUserChatId("chatId");
    }

    @Test
    public void findById_should_handle_to_repository() {
        TelegramUserInputEntity entity = new TelegramUserInputEntity("chatId", InputState.BASE, "value");
        Optional<TelegramUserInputEntity> optional = Optional.of(entity);

        when(repository.findById(any())).thenReturn(optional);

        TelegramUserInput result = service.findById("chatId", InputState.BASE);

        assertEquals(result, entity.toTelegramUserInput());
    }

    @Test
    public void findById_should_throw_when_entity_not_found() {
        when(repository.findById(any())).thenReturn(Optional.empty());

        assertThrows(TelegramUserInputDoesntExistException.class, () -> service.findById("chatId", InputState.BASE));
    }

    @Test
    public void findAllById_should_handle_to_repository() {
        TelegramUserInputEntity entity = new TelegramUserInputEntity("chatId", InputState.BASE, "value");
        when(repository.findAllByTelegramUserChatId("chatId")).thenReturn(java.util.List.of(entity));

        assertEquals(java.util.List.of(entity.toTelegramUserInput()), service.findAllByChatId("chatId"));
    }
}