package github.ricemonger.item_trade_stats_calculator.postgres.repositories;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemSaleEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {

    @Query(value = "SELECT * FROM item_sale s WHERE s.sold_at >= CURRENT_DATE-30", nativeQuery = true)
    List<ItemSaleEntity> findAllForLastMonth();
}
