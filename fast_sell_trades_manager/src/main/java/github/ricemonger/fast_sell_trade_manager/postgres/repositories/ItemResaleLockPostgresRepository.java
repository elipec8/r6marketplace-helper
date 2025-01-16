package github.ricemonger.fast_sell_trade_manager.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemResaleLockEntity;
import github.ricemonger.utilspostgresschema.ids.user.ItemResaleLockEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemResaleLockPostgresRepository extends JpaRepository<ItemResaleLockEntity, ItemResaleLockEntityId> {
}
