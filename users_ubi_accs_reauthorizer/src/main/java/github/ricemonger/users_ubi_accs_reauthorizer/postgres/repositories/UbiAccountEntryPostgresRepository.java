package github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UbiAccountEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, UbiAccountEntryEntityId> {
}
