package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountAuthorizationEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountAuthorizationEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountAuthorizationEntryPostgresRepository extends JpaRepository<UbiAccountAuthorizationEntryEntity, UbiAccountAuthorizationEntryEntityId> {
    Optional<UbiAccountAuthorizationEntryEntity> findByUserTelegramUserChatId(String chatId);

    void deleteByUserTelegramUserChatId(String chatId);
}
