package github.ricemonger.fast_sell_trade_manager.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountStatsPostgresRepository extends JpaRepository<UbiAccountStatsEntity, String> {
}
