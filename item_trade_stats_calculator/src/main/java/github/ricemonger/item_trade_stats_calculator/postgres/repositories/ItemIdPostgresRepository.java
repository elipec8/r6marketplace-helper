package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemIdPostgresRepository extends JpaRepository<ItemIdEntity, String> {
}
