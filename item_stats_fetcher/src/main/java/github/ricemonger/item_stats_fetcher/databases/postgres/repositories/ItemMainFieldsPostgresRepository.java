package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.HashSet;

public interface ItemMainFieldsPostgresRepository extends JpaRepository<ItemEntity, String> {
    @Query("SELECT i.itemId FROM item i")
    HashSet<String> findAllItemIds();
}
