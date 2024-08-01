package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, String> {
    Optional<UbiAccountEntryEntity> findByUserTelegramUserChatId(String chatId);

    void deleteByUserTelegramUserChatId(String chatId);
}
