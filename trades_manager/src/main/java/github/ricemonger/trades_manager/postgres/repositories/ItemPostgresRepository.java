package github.ricemonger.trades_manager.postgres.repositories;


import github.ricemonger.trades_manager.postgres.entities.items.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {

}
