package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.services.DTOs.UbiAccountAuthorizationEntry;
import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.ids.user.UbiAccountEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UbiAccountEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, UbiAccountEntryEntityId> {
    void deleteByUserTelegramUserChatId(String chatId);

    Optional<UbiAccountEntryEntity> findByUserTelegramUserChatId(String chatId);

    Optional<UbiAccountAuthorizationEntry> findUbiAccountAuthorizationEntryByUserTelegramUserChatId(String chatId);
}
