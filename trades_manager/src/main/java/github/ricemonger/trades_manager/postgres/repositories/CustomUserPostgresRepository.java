package github.ricemonger.trades_manager.postgres.repositories;

import github.ricemonger.trades_manager.postgres.dto_projections.ManageableUserProjection;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomUserPostgresRepository extends JpaRepository<UserEntity, String> {

    @Transactional(readOnly = true)
    @Query("SELECT new github.ricemonger.trades_manager.postgres.dto_projections.ManageableUserProjection(" +
           "u.id, " +
           "u.ubiAccountEntry.ubiAccountStats, " +
           "u.ubiAccountEntry.ubiAuthTicket, " +
           "u.ubiAccountEntry.ubiSpaceId, " +
           "u.ubiAccountEntry.ubiSessionId, " +
           "u.ubiAccountEntry.ubiRememberDeviceTicket, " +
           "u.ubiAccountEntry.ubiRememberMeTicket, " +
           "u.tradeByFiltersManagers, " +
           "u.tradeByItemIdManagers, " +
           "u.sellTradesManagingEnabledFlag, " +
           "u.sellTradePriorityExpression, " +
           "u.buyTradesManagingEnabledFlag," +
           "u.buyTradePriorityExpression) " +
           "FROM UserEntity u")
    List<ManageableUserProjection> findAllManageableUsers();
}
