package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.ids.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemFilterPostgresEntity extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ItemFilterEntity i WHERE i.user.telegramUser.chatId = :chatId AND i.name = :name")
    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    @Query("SELECT i.name FROM ItemFilterEntity i WHERE i.user.telegramUser.chatId = :chatId")
    List<ItemFilterEntity> findAllNamesByUserTelegramUserChatId(String chatId);

    @Transactional(readOnly = true)
    Optional<ItemFilterEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    List<ItemFilterEntity> findAllByUserTelegramUserChatId(String chatId);
}
