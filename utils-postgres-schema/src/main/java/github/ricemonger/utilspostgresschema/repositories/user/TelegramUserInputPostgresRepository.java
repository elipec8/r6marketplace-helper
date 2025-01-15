package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserInputEntity;
import github.ricemonger.utilspostgresschema.ids.user.TelegramUserInputEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserInputPostgresRepository extends JpaRepository<TelegramUserInputEntity, TelegramUserInputEntityId> {
}
