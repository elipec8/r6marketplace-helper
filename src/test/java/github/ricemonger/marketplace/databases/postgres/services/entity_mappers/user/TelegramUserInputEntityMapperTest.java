package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.telegramBot.InputState;
import github.ricemonger.utils.DTOs.personal.TelegramUserInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class TelegramUserInputEntityMapperTest {
    @Autowired
    private TelegramUserInputEntityMapper telegramUserInputEntityMapper;
    @MockBean
    private TelegramUserPostgresRepository telegramUserPostgresRepository;

    @Test
    public void createEntity_should_properly_map_entity() {
        TelegramUserInput input = new TelegramUserInput("chatId", InputState.ITEM_FILTER_NAME, "value");

        when(telegramUserPostgresRepository.findById("chatId")).thenReturn(Optional.of(new TelegramUserEntity("chatId", 1L)));

        assertTrue(new TelegramUserInputEntity(new TelegramUserEntity("chatId", 1L), InputState.ITEM_FILTER_NAME, "value").isFullyEqualExceptTelegramUser(
                telegramUserInputEntityMapper.createEntity(input)));
    }

    @Test
    public void createDTO_should_properly_map_dto() {
        TelegramUserInputEntity entity = new TelegramUserInputEntity(new TelegramUserEntity("chatId", 1L), InputState.ITEM_FILTER_NAME, "value");

        assertEquals(new TelegramUserInput("chatId", InputState.ITEM_FILTER_NAME, "value"), telegramUserInputEntityMapper.createDTO(entity));
    }
}