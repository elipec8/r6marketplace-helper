package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.utilspostgresschema.ids.user.TradeByFiltersManagerEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeByFiltersManagerPostgresRepository extends JpaRepository<TradeByFiltersManagerEntity, TradeByFiltersManagerEntityId> {
}
