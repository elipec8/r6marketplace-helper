package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByItemIdManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeByItemIdManagerPostgresRepository extends JpaRepository<TradeByItemIdManagerEntity, TradeByItemIdManagerEntityId> {
    void invertEnabledFlagByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    void deleteByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    Optional<TradeByItemIdManagerEntity> findByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    List<TradeByItemIdManagerEntity> findAllByUserTelegramUserChatId(String chatId);
}
