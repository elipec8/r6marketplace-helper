package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUserInputEntityId;
import github.ricemonger.utils.dtos.TelegramUserInput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId>{
    void deleteAllByChatId(String chatId);

    Collection<TelegramUserInputEntity> findAllByChatId(String chatId);
}
