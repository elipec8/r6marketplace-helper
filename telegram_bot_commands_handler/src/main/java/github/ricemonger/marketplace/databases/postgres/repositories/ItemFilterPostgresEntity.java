package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.ids.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemFilterPostgresEntity extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    Optional<ItemFilterEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    List<ItemFilterEntity> findAllNamesByUserTelegramUserChatId(String chatId);

    List<ItemFilterEntity> findAllByUserTelegramUserChatId(String chatId);
}
