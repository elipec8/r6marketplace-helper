package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId> {
    @Transactional
    void deleteAllByTelegramUserChatId(String chatId);

    @Transactional(readOnly = true)
    List<TelegramUserInputEntity> findAllByTelegramUserChatId(String chatId);
}
