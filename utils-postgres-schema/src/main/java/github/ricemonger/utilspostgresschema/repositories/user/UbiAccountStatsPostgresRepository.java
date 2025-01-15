package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountStatsPostgresRepository extends JpaRepository<UbiAccountStatsEntity, String> {
}
