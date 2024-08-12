package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeManagerByItemFiltersPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByFiltersManagerEntityId> {
}
