package github.ricemonger.utilspostgresschema.repositories.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.utilspostgresschema.ids.item.ItemDaySalesUbiStatsEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemDaySalesUbiStatsPostgresRepository extends JpaRepository<ItemDaySalesUbiStatsEntity, ItemDaySalesUbiStatsEntityId> {
}
