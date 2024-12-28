package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUbiAccountEntryPostgresRepository extends JpaRepository<UserUbiAccountEntryEntity, Long> {
}
