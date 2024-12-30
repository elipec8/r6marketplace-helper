package github.ricemonger.marketplace.databases.postgres.custom.settings.service;

import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.UserEntity;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramUserChatId(String chatId);

    void updateTradeManagersManagingEnabledFlagByTelegramUserChatId(String chatId, boolean flag);

    void updateNewManagersAreActiveFlagByTelegramUserChatId(String chatId, boolean flag);

    void updateItemShowFieldsSettingsByTelegramUserChatId(String chatId, ItemShownFieldsSettings settings);
}
