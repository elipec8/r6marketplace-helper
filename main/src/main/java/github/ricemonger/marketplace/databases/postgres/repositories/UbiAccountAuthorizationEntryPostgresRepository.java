package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountAuthorizationEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, UbiAccountEntryEntityId> {
    Optional<UbiAccountEntryEntity> findByUserTelegramUserChatId(String chatId);
}
