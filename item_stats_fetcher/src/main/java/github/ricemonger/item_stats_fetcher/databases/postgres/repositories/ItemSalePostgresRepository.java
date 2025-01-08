package github.ricemonger.item_stats_fetcher.databases.postgres.repositories;


import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {
    @Transactional
    default void insertAll(List<ItemSaleEntity> itemSaleEntities) {
        for (ItemSaleEntity itemSaleEntity : itemSaleEntities) {
            insert(itemSaleEntity);
        }
    }

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO item_sale (item_id, sold_at, price) VALUES (:#{#itemSaleEntity.item.itemId}, :#{#itemSaleEntity.soldAt}, :#{#itemSaleEntity.price}) ON CONFLICT (item_id, sold_at) DO NOTHING", nativeQuery = true)
    void insert(ItemSaleEntity itemSaleEntity);
}
