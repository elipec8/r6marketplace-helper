package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByItemIdManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TradeByItemIdManagerPostgresRepository extends JpaRepository<TradeByItemIdManagerEntity, TradeByItemIdManagerEntityId> {
    @Transactional
    @Modifying
    @Query("UPDATE TradeByItemIdManagerEntity t SET t.enabled = NOT t.enabled " +
           "WHERE t.user.telegramUser.chatId = :chatId AND t.item.itemId = :itemId")
    void invertEnabledFlagByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    @Transactional
    @Modifying
    //@Query("DELETE FROM TradeByItemIdManagerEntity t WHERE t.user.telegramUser.chatId = :chatId AND t.item.itemId = :itemId")
    void deleteByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    @Transactional(readOnly = true)
    Optional<TradeByItemIdManagerEntity> findByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    @Transactional(readOnly = true)
    List<TradeByItemIdManagerEntity> findAllByUserTelegramUserChatId(String chatId);
}
