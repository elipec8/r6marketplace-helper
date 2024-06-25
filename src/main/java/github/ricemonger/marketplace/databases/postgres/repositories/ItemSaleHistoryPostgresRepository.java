package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSaleHistoryPostgresRepository extends JpaRepository<ItemSaleHistoryEntity, String> {
}
