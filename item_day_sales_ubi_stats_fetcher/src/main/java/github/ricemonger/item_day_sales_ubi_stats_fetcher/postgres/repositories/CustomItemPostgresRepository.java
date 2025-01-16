package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.custom_entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomItemPostgresRepository extends JpaRepository<ItemEntity, String> {

}
