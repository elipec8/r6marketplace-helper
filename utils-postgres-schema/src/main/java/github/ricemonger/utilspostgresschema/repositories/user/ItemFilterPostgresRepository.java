package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import github.ricemonger.utilspostgresschema.ids.user.ItemFilterEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemFilterPostgresRepository extends JpaRepository<ItemFilterEntity, ItemFilterEntityId> {
}
