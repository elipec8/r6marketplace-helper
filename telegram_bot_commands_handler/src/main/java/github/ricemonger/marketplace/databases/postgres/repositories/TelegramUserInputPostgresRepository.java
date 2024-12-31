package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId> {
    @Transactional
    @Modifying
    @Query("DELETE FROM TelegramUserInputEntity t WHERE t.telegramUser.chatId = :chatId")
    void deleteAllByTelegramUserChatId(String chatId);

    @Transactional(readOnly = true)
    Collection<TelegramUserInputEntity> findAllByTelegramUserChatId(String chatId);
}
