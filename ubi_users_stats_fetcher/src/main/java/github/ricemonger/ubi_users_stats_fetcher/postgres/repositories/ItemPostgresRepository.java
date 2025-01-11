package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {
    @Transactional(readOnly = true)
    List<ItemEntity> findAllByItemIdIn(List<String> itemIds);

    @Transactional(readOnly = true)
    @Query("SELECT i.itemId FROM ItemEntity i")
    List<String> findAllItemIds();
}
