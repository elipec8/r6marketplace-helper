package github.ricemonger.marketplace.databases.postgres.custom_repositories.tg_user_item_filter_service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemFilterUserIdPostgresRepository extends JpaRepository<ItemFilterUserIdEntity, ItemFilterUserIdEntityId> {
    List<ItemFilterUserIdEntity> findAllByUserTelegramUserChatId(String chatId);

    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    Optional<ItemFilterUserIdEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);
}
