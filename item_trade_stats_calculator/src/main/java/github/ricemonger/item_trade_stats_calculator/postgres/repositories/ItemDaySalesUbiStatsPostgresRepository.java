package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemDaySalesUbiStatsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemDaySalesUbiStatsPostgresRepository extends JpaRepository<ItemDaySalesUbiStatsEntity, ItemDaySalesUbiStatsEntityId> {
    @Query(value = "SELECT * FROM item_day_sales_ubi_stats s WHERE s.date >= CURRENT_DATE-30", nativeQuery = true)
    List<ItemDaySalesUbiStatsEntity> findAllForLastMonth();
}


