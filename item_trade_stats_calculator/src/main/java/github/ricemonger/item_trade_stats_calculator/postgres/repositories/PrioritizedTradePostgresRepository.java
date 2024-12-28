package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.PrioritizedTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrioritizedTradePostgresRepository extends JpaRepository<PrioritizedTradeEntity, String> {
}
