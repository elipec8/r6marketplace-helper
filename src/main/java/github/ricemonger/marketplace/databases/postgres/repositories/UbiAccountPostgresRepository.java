package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountPostgresRepository extends JpaRepository<UbiAccountEntity, UserEntity> {
}
