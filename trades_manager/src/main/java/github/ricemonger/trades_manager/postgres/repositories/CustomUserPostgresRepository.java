package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomUserPostgresRepository extends JpaRepository<UserEntity, String> {
    @Transactional(readOnly = true)
    @Query("""
            SELECT u FROM UserEntity u
            WHERE  (SIZE(u.tradeByItemIdManagers) > 0 OR SIZE(u.tradeByFiltersManagers) > 0) AND
            u.managingEnabledFlag = true AND u.ubiAccountEntry IS NOT NULL AND u.ubiAccountEntry.ubiAccountStats IS NOT NULL""")
    List<UserEntity> findAllManageableUsers();
}
