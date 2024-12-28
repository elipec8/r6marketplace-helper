package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;

import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats.UbiAccountStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountStatsPostgresRepository extends JpaRepository<UbiAccountStatsEntity, String> {
}
