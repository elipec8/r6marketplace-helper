package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemHistoryFieldsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemHistoryFieldsPostgresRepository extends JpaRepository<ItemHistoryFieldsEntity, String> {
}
