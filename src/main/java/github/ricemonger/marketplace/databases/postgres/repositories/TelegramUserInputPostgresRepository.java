package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId> {
    List<TelegramUserInputEntity> findAllByTelegramUserChatId(String chatId);
}
