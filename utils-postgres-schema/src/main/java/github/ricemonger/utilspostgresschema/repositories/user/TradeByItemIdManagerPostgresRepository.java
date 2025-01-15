package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByItemIdManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeByItemIdManagerPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByItemIdManagerEntityId> {
}
