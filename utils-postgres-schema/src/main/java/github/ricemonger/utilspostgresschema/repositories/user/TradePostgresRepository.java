package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.TradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradePostgresRepository extends JpaRepository<TradeEntity, String> {
}
