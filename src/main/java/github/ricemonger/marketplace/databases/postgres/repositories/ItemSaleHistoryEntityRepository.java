package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSaleHistoryEntityRepository extends JpaRepository<ItemSaleHistoryEntity, String> {
}
