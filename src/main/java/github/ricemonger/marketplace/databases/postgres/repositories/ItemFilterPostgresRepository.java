package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId>{
    Collection<ItemFilterEntity> findAllByUserId(Long userId);
}
