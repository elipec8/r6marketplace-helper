package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {
}
