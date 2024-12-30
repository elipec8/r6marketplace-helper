package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserIdPostgresRepository extends JpaRepository<TelegramUserIdEntity, String> {
}
