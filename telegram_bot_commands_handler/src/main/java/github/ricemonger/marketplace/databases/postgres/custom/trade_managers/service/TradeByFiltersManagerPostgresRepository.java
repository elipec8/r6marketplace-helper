package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.service;


import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.TradeByFiltersManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeByFiltersManagerPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByFiltersManagerEntityId> {
    List<TradeByFiltersManagerEntity> findAllByUserTelegramUserChatId(String chatId);

    Optional<TradeByFiltersManagerEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    void invertEnabledFlagByUserTelegramUserChatIdAndName(String chatId, String name);
}
