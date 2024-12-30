package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.service;


import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.TradeByItemIdManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeByItemIdManagerPostgresRepository extends JpaRepository<TradeByItemIdManagerEntity, TradeByItemIdManagerEntityId> {
    List<TradeByItemIdManagerEntity> findAllByUserTelegramUserChatId(String chatId);

    Optional<TradeByItemIdManagerEntity> findByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    void deleteByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);

    void invertEnabledFlagByUserTelegramUserChatIdAndItemItemId(String chatId, String itemId);
}
