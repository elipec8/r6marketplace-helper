package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {
    @Query("SELECT i.itemId FROM item i")
    List<String> findAllItemIds();
}
