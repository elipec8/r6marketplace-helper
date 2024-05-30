package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId>{
    void deleteAllByChatId(String chatId);
}
