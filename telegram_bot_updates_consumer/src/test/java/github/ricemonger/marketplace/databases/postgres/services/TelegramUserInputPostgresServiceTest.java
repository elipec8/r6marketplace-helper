package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.dto_projections.TelegramUserInputProjection;
import github.ricemonger.marketplace.databases.postgres.repositories.CustomTelegramUserInputPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TelegramUserInputEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.server.TelegramUserInputDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private CustomTelegramUserInputPostgresRepository telegramUserInputRepository;
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
        inputService.deleteAllByChatId("chatId");

        verify(telegramUserInputRepository).deleteAllByChatId("chatId");
    }

    @Test
    public void findById_should_return_mapped_dto() throws TelegramUserDoesntExistException, TelegramUserInputDoesntExistException {
        TelegramUserEntity user = new TelegramUserEntity();
        user.setChatId("chatId");
        TelegramUserInputProjection projection = new TelegramUserInputProjection();
        TelegramUserInput input = new TelegramUserInput("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");

        when(telegramUserInputRepository.findInputById("chatId",InputState.UBI_ACCOUNT_ENTRY_2FA_CODE)).thenReturn(Optional.of(projection));
        when(telegramUserInputEntityMapper.createDTO(same(projection))).thenReturn(input);

        TelegramUserInput result = inputService.findById("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE);

        assertSame(input, result);
    }

    @Test
    public void findAllByChatId_should_return_mapped_dtos() throws TelegramUserDoesntExistException {
        List<TelegramUserInputProjection> entities = new ArrayList<>();
        TelegramUserInputProjection projection1 = new TelegramUserInputProjection();
        TelegramUserInputProjection projection2 = new TelegramUserInputProjection();
        entities.add(projection1);
        entities.add(projection2);
        TelegramUserInput input1 = new TelegramUserInput("chatId", InputState.UBI_ACCOUNT_ENTRY_2FA_CODE, "value");
        TelegramUserInput input2 = new TelegramUserInput("chatId", InputState.ITEM_FILTER_ITEM_NAME_PATTERNS, "value1");

        when(telegramUserInputRepository.findAllByChatId("chatId")).thenReturn(entities);
        when(telegramUserInputEntityMapper.createDTO(same(projection1))).thenReturn(input1);
        when(telegramUserInputEntityMapper.createDTO(same(projection2))).thenReturn(input2);

        List<TelegramUserInput> result = inputService.findAllByChatId("chatId");

        assertEquals(2, result.size());
        assertTrue(result.contains(input1));
        assertTrue(result.contains(input2));
    }
}