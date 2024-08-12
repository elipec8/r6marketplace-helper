package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeManagerByItemIdPostgresRepository extends JpaRepository<TradeByItemIdManagerEntity, TradeByItemIdManagerEntityId> {
    List<TradeByItemIdManagerEntity> findAllByUserId(Long userId);
}
