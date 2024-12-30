package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.custom.inputs.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.custom.inputs.service.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserInputEntityMapper {
    private final TelegramUserPostgresRepository telegramUserIdPostgresRepository;

    public TelegramUserInputEntity createEntity(TelegramUserInput input) {
        return new TelegramUserInputEntity(
                telegramUserIdPostgresRepository.findById(input.getChatId()).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + input.getChatId() + " not found")),
                input.getInputState(),
                input.getValue());
    }

    public TelegramUserInput createDTO(TelegramUserInputEntity entity) {
        return new TelegramUserInput(entity.getChatId_(), entity.getInputState(), entity.getValue());
    }
}
