package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service.TelegramUserIdInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserInputEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class TelegramUserInputPostgresServiceTest {
    @Autowired
    private TelegramUserInputPostgresService inputService;
    @MockBean
    private TelegramUserIdInputPostgresRepository telegramUserInputRepository;
    @MockBean
    private TelegramUserPostgresRepository telegramUserRepository;
    @MockBean
    private TelegramUserInputEntityMapper telegramUserInputEntityMapper;

    @Test
    public void save_should_map_and_save_dto() throws TelegramUserDoesntExistException {
        TelegramUserInput input = new TelegramUserInput("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");
        TelegramUserInputEntity entity = new TelegramUserInputEntity();

        when(telegramUserInputEntityMapper.createEntity(eq(input))).thenReturn(entity);

        inputService.save("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");

        verify(telegramUserInputRepository).save(same(entity));
    }

    @Test
    public void deleteAllByChatId_should_clear_and_save_user() throws TelegramUserDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");
        List<TelegramUserInputEntity> inputs = new ArrayList<>();
        inputs.add(new TelegramUserInputEntity(user, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value"));
        inputs.add(new TelegramUserInputEntity(user, InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "value1"));
        user.setTelegramUserInputs(inputs);

        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));

        inputService.deleteAllByChatId("chatId");

        assertEquals(0, user.getTelegramUserInputs().size());
        verify(telegramUserRepository).save(same(user));
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");
        TelegramUserInputEntityId id = new TelegramUserInputEntityId(user, InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);
        TelegramUserInputEntity entity = new TelegramUserInputEntity();
        TelegramUserInput input = new TelegramUserInput("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");

        when(telegramUserRepository.findById("chatId")).thenReturn(Optional.of(user));
        when(telegramUserInputRepository.findById(eq(id))).thenReturn(Optional.of(entity));
        when(telegramUserInputEntityMapper.createDTO(same(entity))).thenReturn(input);

        TelegramUserInput result = inputService.findById("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);

        assertEquals(input, result);
    }

    @Test
    public void findAllByChatId_should_return_mapped_dtos() throws TelegramUserDoesntExistException {
        List<TelegramUserInputEntity> entities = new ArrayList<>();
        TelegramUserInputEntity entity1 = new TelegramUserInputEntity();
        TelegramUserInputEntity entity2 = new TelegramUserInputEntity();
        entities.add(entity1);
        entities.add(entity2);
        TelegramUserInput input1 = new TelegramUserInput("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");
        TelegramUserInput input2 = new TelegramUserInput("chatId", InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "value1");

        when(telegramUserInputRepository.findAllByTelegramUserChatId("chatId")).thenReturn(entities);
        when(telegramUserInputEntityMapper.createDTO(same(entity1))).thenReturn(input1);
        when(telegramUserInputEntityMapper.createDTO(same(entity2))).thenReturn(input2);

        List<TelegramUserInput> result = inputService.findAllByChatId("chatId");

        assertEquals(2, result.size());
        assertTrue(result.contains(input1));
        assertTrue(result.contains(input2));
    }
}