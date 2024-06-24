package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedTradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedTradeManagerByItemIdEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TradeManagerByItemIdPostgresRepository extends JpaRepository<TelegramLinkedTradeManagerByItemIdEntity, TelegramLinkedTradeManagerByItemIdEntityId> {
    Collection<TelegramLinkedTradeManagerByItemIdEntity> findAllByChatId(String chatId);
}
