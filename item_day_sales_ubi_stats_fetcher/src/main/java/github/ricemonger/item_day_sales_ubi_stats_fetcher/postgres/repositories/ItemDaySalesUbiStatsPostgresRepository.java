package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories;


import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item.ItemDaySalesUbiStatsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDaySalesUbiStatsPostgresRepository extends JpaRepository<ItemDaySalesUbiStatsEntity, ItemDaySalesUbiStatsEntityId> {

}


