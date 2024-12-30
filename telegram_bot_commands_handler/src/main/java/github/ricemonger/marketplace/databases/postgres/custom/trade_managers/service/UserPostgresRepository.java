package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.service;

import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostgresRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByTelegramUserChatId(String chatId);
}
