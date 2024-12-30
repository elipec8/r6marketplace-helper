package github.ricemonger.marketplace.databases.postgres.custom.inputs.service;

import github.ricemonger.marketplace.databases.postgres.custom.inputs.entities.TelegramUserInputEntity;
import github.ricemonger.marketplace.databases.postgres.custom.inputs.entities.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId> {
    List<TelegramUserInputEntity> findAllByTelegramUserChatId(String chatId);

    void deleteAllByTelegramUserChatId(String chatId);
}
