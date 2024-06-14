package github.ricemonger.marketplace.databases.postgres.repositories;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntityId;
import github.ricemonger.utils.dtos.ItemFilter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId>{
    Collection<ItemFilterEntity> findAllByChatId(String chatId);
}
