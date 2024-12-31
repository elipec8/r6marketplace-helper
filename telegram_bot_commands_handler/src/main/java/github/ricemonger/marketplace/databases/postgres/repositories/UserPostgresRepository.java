package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection;
import github.ricemonger.marketplace.services.DTOs.ItemShowSettings;
import github.ricemonger.marketplace.services.DTOs.ItemShownFieldsSettings;
import github.ricemonger.marketplace.services.DTOs.TradeManagersSettings;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
    @Transactional(readOnly = true)
    Optional<UserEntity> findByTelegramUserChatId(String chatId);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
           "u.itemShowNameFlag = :#{#settings.itemShowNameFlag}, " +
           "u.itemShowItemTypeFlag = :#{#settings.itemShowItemTypeFlag}, " +
           "u.itemShowMaxBuyPrice = :#{#settings.itemShowMaxBuyPrice}, " +
           "u.itemShowBuyOrdersCountFlag = :#{#settings.itemShowBuyOrdersCountFlag}, " +
           "u.itemShowMinSellPriceFlag = :#{#settings.itemShowMinSellPriceFlag}, " +
           "u.itemsShowSellOrdersCountFlag = :#{#settings.itemsShowSellOrdersCountFlag}, " +
           "u.itemShowPictureFlag = :#{#settings.itemShowPictureFlag} " +
           "WHERE u.telegramUser.chatId = :chatId")
    void updateItemShowFieldsSettingsByTelegramUserChatId(String chatId, ItemShownFieldsSettings settings);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
           "u.newManagersAreActiveFlag = :#{#flag} " +
           "WHERE u.telegramUser.chatId = :chatId")
    void updateNewManagersAreActiveFlagByTelegramUserChatId(String chatId, boolean flag);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET " +
           "u.managingEnabledFlag = :#{#flag} " +
           "WHERE u.telegramUser.chatId = :chatId")
    void updateTradeManagersManagingEnabledFlagByTelegramUserChatId(String chatId, boolean flag);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.marketplace.services.DTOs.TradeManagersSettings(u.newManagersAreActiveFlag, u.managingEnabledFlag) " +
           "FROM UserEntity u WHERE u.telegramUser.chatId = :chatId")
    Optional<TradeManagersSettings> findTradeManagersSettingsByTelegramUserChatId(String chatId);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.itemShowAppliedFilters = CONCAT(u.itemShowAppliedFilters, :itemFilterEntity) " +
           "WHERE u.telegramUser.chatId = :chatId")
    void addItemShowAppliedFilterByTelegramUserChatId(String chatId, ItemFilterEntity itemFilterEntity);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.itemShowAppliedFilters = REMOVE(u.itemShowAppliedFilters, :itemFilterEntity) " +
           "WHERE u.telegramUser.chatId = :chatId")
    void removeItemShowAppliedFilterByTelegramUserChatId(String chatId, ItemFilterEntity itemFilterEntity);

    @Transactional(readOnly = true)
    @Query("SELECT f.name FROM UserEntity u JOIN u.itemShowAppliedFilters f WHERE u.telegramUser.chatId = :chatId")
    List<String> findAllUserItemShowAppliedFiltersNamesByTelegramUserChatId(String chatId);

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.marketplace.databases.postgres.dto_projections.ItemShowSettingsProjection(" +
           "u.telegramUser.itemShowMessagesLimit, " +
           "u.telegramUser.itemShowFewInMessageFlag, " +
           "u.itemShowNameFlag, " +
           "u.itemShowItemTypeFlag, " +
           "u.itemShowMaxBuyPrice, " +
           "u.itemShowBuyOrdersCountFlag, " +
           "u.itemShowMinSellPriceFlag, " +
           "u.itemsShowSellOrdersCountFlag, " +
           "u.itemShowPictureFlag, " +
           "u.itemShowAppliedFilters) " +
           "FROM UserEntity u WHERE u.telegramUser.chatId = :chatId")
    Optional<ItemShowSettingsProjection> findItemShowSettingsByTelegramUserChatId(String chatId);
}
