package github.ricemonger.utilspostgresschema.repositories.item;

import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPostgresRepository extends JpaRepository<ItemEntity, String> {
}
