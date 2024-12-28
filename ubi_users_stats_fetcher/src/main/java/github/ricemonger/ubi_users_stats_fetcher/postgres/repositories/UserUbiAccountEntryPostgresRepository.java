package github.ricemonger.ubi_users_stats_fetcher.postgres.repositories;


import github.ricemonger.ubi_users_stats_fetcher.postgres.entities.user_ubi_account_entry.UserUbiAccountEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserUbiAccountEntryPostgresRepository extends JpaRepository<UserUbiAccountEntryEntity, Long> {
}
