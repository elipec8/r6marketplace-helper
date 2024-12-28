package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.entities.ubi_account_stats.UbiAccountStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountStatsPostgresRepository extends JpaRepository<UbiAccountStatsEntity, String> {
}
