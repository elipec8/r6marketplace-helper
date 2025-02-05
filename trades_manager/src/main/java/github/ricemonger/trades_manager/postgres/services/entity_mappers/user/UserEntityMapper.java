package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.dto_projections.ManageableUserProjection;
import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import github.ricemonger.trades_manager.services.DTOs.UbiAccountStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    private final TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    private final UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    public ManageableUser createManageableUser(ManageableUserProjection projection) {

        UbiAccountStats ubiAccountStats = ubiAccountStatsEntityMapper.createDTO(projection.getUbiAccountStats());

        return new ManageableUser(
                projection.getId(),
                ubiAccountStats,
                projection.getUbiAuthTicket(),
                projection.getUbiSpaceId(),
                projection.getUbiSessionId(),
                projection.getUbiRememberDeviceTicket(),
                projection.getUbiRememberMeTicket(),
                projection.getTradeByFiltersManagers().stream().map(tradeByFiltersManagerEntityMapper::createDTO).toList(),
                projection.getTradeByItemIdManagers().stream().map(tradeByItemIdManagerEntityMapper::createDTO).toList(),
                projection.getSellTradesManagingEnabledFlag(),
                projection.getSellTradePriorityExpression(),
                projection.getBuyTradesManagingEnabledFlag(),
                projection.getBuyTradePriorityExpression()
        );
    }
}
