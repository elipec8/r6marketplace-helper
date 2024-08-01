package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
    List<ItemFilterEntity> findAllByUserId(Long userId);
}
