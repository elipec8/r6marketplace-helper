package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountPostgresRepository extends JpaRepository<UbiAccountEntity, String> {
    Optional<UbiAccountEntity> findByUserTelegramUserChatId(String chatId);
    void deleteByUserTelegramUserChatId(String chatId);
}
