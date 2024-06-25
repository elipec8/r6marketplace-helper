package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemFiltersEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemFiltersEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeManagerByItemFiltersPostgresRepository extends JpaRepository<TradeManagerByItemFiltersEntity, TradeManagerByItemFiltersEntityId> {
}
