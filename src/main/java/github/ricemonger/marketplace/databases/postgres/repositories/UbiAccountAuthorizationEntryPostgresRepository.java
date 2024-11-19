package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountAuthorizationEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountAuthorizationEntryPostgresRepository extends JpaRepository<UbiAccountAuthorizationEntryEntity, String> {
    Optional<UbiAccountAuthorizationEntryEntity> findByUserTelegramUserChatId(String chatId);

    void deleteByUserTelegramUserChatId(String chatId);
}
