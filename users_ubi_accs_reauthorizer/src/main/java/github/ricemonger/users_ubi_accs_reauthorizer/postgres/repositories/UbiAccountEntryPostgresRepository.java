package github.ricemonger.users_ubi_accs_reauthorizer.postgres.repositories;

import github.ricemonger.users_ubi_accs_reauthorizer.postgres.dto_projections.UnauthorizedAccountProjection;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntity;
import github.ricemonger.users_ubi_accs_reauthorizer.postgres.entities.UbiAccountEntryEntityId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface UbiAccountEntryPostgresRepository extends JpaRepository<UbiAccountEntryEntity, UbiAccountEntryEntityId> {
    @Transactional
    default void deleteAllUbiAccountStatsForUnauthorizedUsers(Collection<UnauthorizedAccountProjection> projections) {
        for (UnauthorizedAccountProjection projection : projections) {
            deleteUbiAccountStatsForUnauthorizedUser(projection);
        }
    }

    @Transactional
    @Modifying
    @Query("UPDATE UbiAccountEntryEntity u SET u.ubiAccountStats = NULL WHERE u.user.id = :#{#projection.id} AND u.email = :#{#projection.email}")
    void deleteUbiAccountStatsForUnauthorizedUser(UnauthorizedAccountProjection projection);
}
