package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramLinkedUserEntityRepository extends JpaRepository<TelegramLinkedUserEntity, String> {
}
