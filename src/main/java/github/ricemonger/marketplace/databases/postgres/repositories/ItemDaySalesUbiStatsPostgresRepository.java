package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemDaySalesUbiStatsPostgresRepository extends JpaRepository<ItemDaySalesUbiStatsEntity, ItemDaySalesUbiStatsEntityId> {
    List<ItemDaySalesUbiStatsEntity> findAllByItemItemId(String itemId);
}


