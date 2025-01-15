package github.ricemonger.utilspostgresschema.repositories.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemSaleEntity;
import github.ricemonger.utilspostgresschema.ids.item.ItemSaleEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSalePostgresRepository extends JpaRepository<ItemSaleEntity, ItemSaleEntityId> {
}
