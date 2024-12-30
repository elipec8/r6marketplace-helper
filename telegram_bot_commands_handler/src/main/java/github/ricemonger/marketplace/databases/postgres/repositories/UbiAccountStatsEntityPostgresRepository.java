package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.UbiAccountStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountStatsEntityPostgresRepository extends JpaRepository<UbiAccountStatsEntity, String> {
}
