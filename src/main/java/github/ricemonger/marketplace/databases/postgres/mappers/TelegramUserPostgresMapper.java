package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserEntity;
import github.ricemonger.utils.dtos.TelegramUser;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class TelegramUserPostgresMapper {

    public Collection<TelegramUserEntity> mapTelegramUserEntities(Collection<TelegramUser> telegramUsers) {
        if (telegramUsers == null || telegramUsers.isEmpty()) {
            return List.of();
        } else {
            return telegramUsers.stream().map(this::mapTelegramUserEntity).toList();
        }
    }

    public TelegramUserEntity mapTelegramUserEntity(TelegramUser telegramUser) {
        TelegramUserEntity telegramUserEntity = new TelegramUserEntity();

        telegramUserEntity.setChatId(telegramUser.getChatId());
        telegramUserEntity.setInputState(telegramUser.getInputState());
        telegramUserEntity.setInputGroup(telegramUser.getInputGroup());
        telegramUserEntity.setPublicNotificationsEnabledFlag(telegramUser.isPublicNotificationsEnabledFlag());

        return telegramUserEntity;
    }

    public Collection<TelegramUser> mapTelegramUsers(Collection<TelegramUserEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapTelegramUser).toList();
        }
    }

    public TelegramUser mapTelegramUser(TelegramUserEntity entity) {
        TelegramUser telegramUser = new TelegramUser();

        telegramUser.setChatId(entity.getChatId());
        telegramUser.setInputState(entity.getInputState());
        telegramUser.setInputGroup(entity.getInputGroup());
        telegramUser.setPublicNotificationsEnabledFlag(entity.isPublicNotificationsEnabledFlag());

        return telegramUser;
    }
}
