package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.ItemIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemIdPostgresRepository extends JpaRepository<ItemIdEntity, String> {
}
