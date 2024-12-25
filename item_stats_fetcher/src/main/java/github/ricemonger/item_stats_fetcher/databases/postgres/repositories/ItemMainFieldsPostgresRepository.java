package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemMainFieldsPostgresRepository extends JpaRepository<ItemMainFieldsEntity, String> {
}
