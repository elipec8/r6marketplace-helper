package github.ricemonger.marketplace.databases.postgres.custom.trade_managers.service;

import github.ricemonger.marketplace.databases.postgres.custom.trade_managers.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {
}
