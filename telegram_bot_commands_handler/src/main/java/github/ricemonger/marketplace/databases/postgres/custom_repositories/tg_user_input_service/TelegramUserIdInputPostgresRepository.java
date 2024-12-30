package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_input_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserIdInputPostgresRepository extends JpaRepository<TelegramUserIdInputEntity, TelegramUserIdInputEntityId> {
    List<TelegramUserIdInputEntity> findAllByTelegramUserChatId(String chatId);

    void deleteAllByTelegramUserChatId(String chatId);
}
