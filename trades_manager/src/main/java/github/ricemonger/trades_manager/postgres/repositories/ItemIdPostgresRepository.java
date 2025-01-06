package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemIdPostgresRepository extends JpaRepository<ItemIdEntity, String> {
}
