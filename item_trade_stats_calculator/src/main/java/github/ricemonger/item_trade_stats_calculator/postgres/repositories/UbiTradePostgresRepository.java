package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.UbiTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiTradePostgresRepository extends JpaRepository<UbiTradeEntity, String> {
}
