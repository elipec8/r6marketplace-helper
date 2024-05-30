package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.utils.dtos.TelegramUser;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TelegramUserPostgresMapper {

    public Collection<TelegramUser> mapTelegramUsers(List<TelegramUserEntity> entities) {
        return entities.stream().map(this::mapTelegramUser).toList();
    }

    public TelegramUser mapTelegramUser(TelegramUserEntity entity) {
        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setChatId(entity.getChatId());
        telegramUser.setInputState(entity.getInputState());
        telegramUser.setInputGroup(entity.getInputGroup());
        telegramUser.setPublicNotificationsEnabledFlag(entity.isPublicNotificationsEnabledFlag());

        return telegramUser;
    }

    public TelegramUserEntity mapTelegramUserEntity(TelegramUser telegramUser) {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();

        telegramUserEntity.setChatId(telegramUser.getChatId());
        telegramUserEntity.setInputState(telegramUser.getInputState());
        telegramUserEntity.setInputGroup(telegramUser.getInputGroup());
        telegramUserEntity.setPublicNotificationsEnabledFlag(telegramUser.isPublicNotificationsEnabledFlag());

        return telegramUserEntity;
    }

    public TelegramUserInputEntity mapTelegramUserInputEntity(TelegramUserInput telegramUserInput) {
        TelegramUserInputEntity telegramUserInputEntity = new TelegramUserInputEntity();

        telegramUserInputEntity.setChatId(telegramUserInput.getChatId());
        telegramUserInputEntity.setInputState(telegramUserInput.getInputState());
        telegramUserInputEntity.setValue(telegramUserInput.getValue());

        return telegramUserInputEntity;
    }

    public TelegramUserInput mapTelegramUserInput(TelegramUserInputEntity telegramUserInputEntity) {
        TelegramUserInput telegramUserInput = new TelegramUserInput();

        telegramUserInput.setChatId(telegramUserInputEntity.getChatId());
        telegramUserInput.setInputState(telegramUserInputEntity.getInputState());
        telegramUserInput.setValue(telegramUserInputEntity.getValue());

        return telegramUserInput;
    }
}
