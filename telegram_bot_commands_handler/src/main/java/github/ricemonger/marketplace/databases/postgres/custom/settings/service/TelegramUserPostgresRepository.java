package github.ricemonger.marketplace.databases.postgres.custom.settings.service;

import github.ricemonger.marketplace.databases.postgres.custom.settings.entities.TelegramUserEntity;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserPostgresRepository extends JpaRepository<TelegramUserEntity, String> {
    void updateInputGroup(String chatId, InputGroup inputGroup);

    void updateInputState(String chatId, InputState inputState);

    void updateInputStateAndGroup(String chatId, InputState inputState, InputGroup inputGroup);

    void updateItemShowFewItemsInMessageFlag(String chatId, boolean flag);

    void updateItemShowMessagesLimit(String chatId, Integer limit);
}
