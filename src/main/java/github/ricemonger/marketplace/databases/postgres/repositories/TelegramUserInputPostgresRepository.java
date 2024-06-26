package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId>{
    void deleteAllByTelegramUserChatId(String chatId);

    Collection<TelegramUserInputEntity> findAllByTelegramUserChatId(String chatId);
}
