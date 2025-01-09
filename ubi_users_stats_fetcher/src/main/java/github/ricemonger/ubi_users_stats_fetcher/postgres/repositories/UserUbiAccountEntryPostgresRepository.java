package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;


import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserUbiAccountEntryPostgresRepository extends JpaRepository<UserUbiAccountEntryEntity, Long> {
    @Transactional(readOnly = true)
    @Query("SELECT u FROM UserUbiAccountEntryEntity u WHERE u.ubiAccountEntry IS NOT NULL AND u.ubiAccountEntry.ubiAccountStats IS NOT NULL")
    List<UserUbiAccountEntryEntity> findAllUserWithAuthorizedUbiAccounts();
}
