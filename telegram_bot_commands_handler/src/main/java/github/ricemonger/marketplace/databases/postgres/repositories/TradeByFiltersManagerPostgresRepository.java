package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByFiltersManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TradeByFiltersManagerPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByFiltersManagerEntityId> {
    @Transactional
    @Modifying
    @Query("UPDATE TradeByFiltersManagerEntity t SET t.enabled = NOT t.enabled " +
           "WHERE t.user.telegramUser.chatId = :chatId AND t.name = :name")
    void invertEnabledFlagByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional
    @Modifying
    @Query("DELETE FROM TradeByFiltersManagerEntity t WHERE t.user.telegramUser.chatId = :chatId AND t.name = :name")
    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    Optional<TradeByFiltersManagerEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    List<TradeByFiltersManagerEntity> findAllByUserTelegramUserChatId(String chatId);
}
