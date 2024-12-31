package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import github.ricemonger.utilspostgresschema.id_entities.user.IdUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByTelegramUserChatId(String chatId);

    void updateItemShowFieldsSettingsByTelegramUserChatId(String chatId, ItemShownFieldsSettings settings);

    void updateNewManagersAreActiveFlagByTelegramUserChatId(String chatId, boolean flag);

    void updateTradeManagersManagingEnabledFlagByTelegramUserChatId(String chatId, boolean flag);

    Optional<TradeManagersSettings> findTradeManagersSettingsByTelegramUserChatId(String chatId);

    void addItemShowAppliedFilterByTelegramUserChatId(String chatId, ItemFilterEntity itemFilterEntity);

    void removeItemShowAppliedFilterByTelegramUserChatId(String chatId, ItemFilterEntity itemFilterEntity);

    List<String> findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(String chatId);

    Optional<ItemShowSettings> findItemShowSettingsByTelegramUserChatId(String chatId);

    Optional<IdUserEntity> findIdEntityByTelegramUserChatId(String chatId);
}
