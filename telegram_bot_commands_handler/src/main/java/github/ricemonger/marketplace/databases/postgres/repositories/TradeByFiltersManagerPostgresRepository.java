package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByFiltersManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TradeByFiltersManagerPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByFiltersManagerEntityId> {
    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    Optional<TradeByFiltersManagerEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    List<TradeByFiltersManagerEntity> findAllByUserTelegramUserChatId(String chatId);

    void invertEnabledFlagByUserTelegramUserChatIdAndName(String chatId, String name);
}
