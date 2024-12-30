package github.ricemonger.marketplace.databases.postgres.custom.item_filters.service;

import github.ricemonger.marketplace.databases.postgres.custom.item_filters.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.custom.item_filters.entities.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
    List<ItemFilterEntity> findAllByUserTelegramUserChatId(String chatId);

    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    Optional<ItemFilterEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);
}
