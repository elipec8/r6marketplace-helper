package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {

}
