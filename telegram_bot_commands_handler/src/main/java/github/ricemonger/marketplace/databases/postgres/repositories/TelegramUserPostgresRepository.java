package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.services.DTOs.TelegramUserInputStateAndGroup;
import github.ricemonger.utils.enums.InputGroup;
import github.ricemonger.utils.enums.InputState;
import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TelegramUserPostgresRepository extends JpaRepository<TelegramUserEntity, String> {
    void updateInputGroup(String chatId, InputGroup inputGroup);

    void updateInputState(String chatId, InputState inputState);

    void updateInputStateAndGroup(String chatId, InputState inputState, InputGroup inputGroup);

    void updateItemShowFewItemsInMessageFlag(String chatId, boolean flag);

    void updateItemShowMessagesLimit(String chatId, Integer limit);

    Optional<TelegramUserInputStateAndGroup> findTelegramUserInputStateAndGroupByChatId(String chatId);
}
