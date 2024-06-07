package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramUbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramUbiUserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbiUserPostgresRepository extends JpaRepository<TelegramUbiUserEntity, TelegramUbiUserEntityId> {
    void deleteAllByChatId(String chatId);

    List<TelegramUbiUserEntity> findAllByChatId(String chatId);
}
