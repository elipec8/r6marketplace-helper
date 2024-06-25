package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeManagerByItemIdPostgresRepository extends JpaRepository<TradeManagerByItemIdEntity, TradeManagerByItemIdEntityId> {
    List<TradeManagerByItemIdEntity> findAllByUserId(Long userId);
}
