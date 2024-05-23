package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramInputValueEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramInputValueEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramInputValueEntityRepository extends JpaRepository<TelegramInputValueEntity, TelegramInputValueEntityId>{
    void deleteAllByChatId(String chatId);
}
