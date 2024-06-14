package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUbiUserEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UbiUserPostgresRepository extends JpaRepository<TelegramLinkedUbiUserEntity, TelegramLinkedUbiUserEntityId> {
    void deleteAllByChatId(String chatId);

    List<TelegramLinkedUbiUserEntity> findAllByChatId(String chatId);
}
