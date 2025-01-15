package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.TelegramUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramUserPostgresRepository extends JpaRepository<TelegramUserEntity, String> {
}
