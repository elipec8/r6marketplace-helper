package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.item.ItemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;

public interface ItemPostgresRepository extends JpaRepository<ItemIdEntity, String> {

}
