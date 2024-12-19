package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.utils.DTOs.TelegramUserInput;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserInputEntityMapper {
    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    public TelegramUserInputEntity createEntity(TelegramUserInput input) {
        return new TelegramUserInputEntity(telegramUserPostgresRepository.findById(
                input.getChatId()).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + input.getChatId() + " not found")),
                input.getInputState(),
                input.getValue());
    }

    public TelegramUserInput createDTO(TelegramUserInputEntity entity) {
        return new TelegramUserInput(entity.getChatId(), entity.getInputState(), entity.getValue());
    }
}
