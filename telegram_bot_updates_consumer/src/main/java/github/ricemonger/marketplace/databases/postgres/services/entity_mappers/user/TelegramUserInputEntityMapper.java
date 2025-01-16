package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;


import github.ricemonger.marketplace.databases.postgres.dto_projections.TelegramUserInputProjection;
import github.ricemonger.marketplace.databases.postgres.repositories.CustomTelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.DTOs.TelegramUserInput;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramUserInputEntityMapper {
    private final CustomTelegramUserPostgresRepository customTelegramUserPostgresRepository;

    public TelegramUserInputEntity createEntity(TelegramUserInput input) {
        if (customTelegramUserPostgresRepository.existsById(input.getChatId())) {
            TelegramUserEntity telegramUserEntity = customTelegramUserPostgresRepository.getReferenceById(input.getChatId());
            TelegramUserInputEntity entity = new TelegramUserInputEntity();
            entity.setTelegramUser(telegramUserEntity);
            entity.setInputState(input.getInputState());
            entity.setValue(input.getValue());
            return entity;
        } else {
            throw new TelegramUserDoesntExistException("Telegram user with chatId " + input.getChatId() + " not found");
        }
    }

    public TelegramUserInput createDTO(TelegramUserInputEntity entity) {
        return new TelegramUserInput(entity.getChatId_(), entity.getInputState(), entity.getValue());
    }

    public TelegramUserInput createDTO(TelegramUserInputProjection projection) {
        return new TelegramUserInput(projection.getChatId(), projection.getInputState(), projection.getValue());
    }
}
