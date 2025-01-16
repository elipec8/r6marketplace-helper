package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.ids.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
    @Transactional
    void deleteByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    Optional<ItemFilterEntity> findByUserTelegramUserChatIdAndName(String chatId, String name);

    @Transactional(readOnly = true)
    @Query("SELECT i.name FROM ItemFilterEntity i WHERE i.user.telegramUser.chatId = :chatId")
    List<String> findAllNamesByUserTelegramUserChatId(String chatId);

    @Transactional(readOnly = true)
    List<ItemFilterEntity> findAllByUserTelegramUserChatId(String chatId);
}
