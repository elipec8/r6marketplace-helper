package github.ricemonger.utilspostgresschema.repositories.user;

import github.ricemonger.utilspostgresschema.full_entities.user.UbiAccountEntryEntity;
import github.ricemonger.utilspostgresschema.ids.user.UbiAccountEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, UbiAccountEntryEntityId> {
}
