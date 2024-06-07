package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TelegramUserInputPostgresMapper {

    public Collection<TelegramUserInputEntity> mapTelegramUserInputEntities(Collection<TelegramUserInput> telegramUserInputs) {
        if (telegramUserInputs == null || telegramUserInputs.isEmpty()) {
            return List.of();
        }
        else {
            return telegramUserInputs.stream().map(this::mapTelegramUserInputEntity).toList();
        }
    }

    public TelegramUserInputEntity mapTelegramUserInputEntity(TelegramUserInput telegramUserInput) {
        TelegramUserInputEntity telegramUserInputEntity = new TelegramUserInputEntity();

        telegramUserInputEntity.setChatId(telegramUserInput.getChatId());
        telegramUserInputEntity.setInputState(telegramUserInput.getInputState());
        telegramUserInputEntity.setValue(telegramUserInput.getValue());

        return telegramUserInputEntity;
    }

    public Collection<TelegramUserInput> mapTelegramUserInputs(Collection<TelegramUserInputEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        }
        else {
            return entities.stream().map(this::mapTelegramUserInput).toList();
        }
    }

    public TelegramUserInput mapTelegramUserInput(TelegramUserInputEntity telegramUserInputEntity) {
        TelegramUserInput telegramUserInput = new TelegramUserInput();

        telegramUserInput.setChatId(telegramUserInputEntity.getChatId());
        telegramUserInput.setInputState(telegramUserInputEntity.getInputState());
        telegramUserInput.setValue(telegramUserInputEntity.getValue());

        return telegramUserInput;
    }
}
