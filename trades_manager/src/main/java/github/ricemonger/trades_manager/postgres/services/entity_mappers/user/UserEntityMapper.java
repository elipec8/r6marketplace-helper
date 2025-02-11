package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.services.DTOs.ManageableUser;
import github.ricemonger.trades_manager.services.DTOs.UbiAccountStats;
import github.ricemonger.utilspostgresschema.full_entities.user.UserEntity;
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

    public ManageableUser createManageableUser(UserEntity entity) {
        UbiAccountStats ubiAccountStats = ubiAccountStatsEntityMapper.createDTO(entity.getUbiAccountEntry().getUbiAccountStats());

        return new ManageableUser(
                entity.getId(),
                ubiAccountStats,
                entity.getUbiAccountEntry().getUbiAuthTicket(),
                entity.getUbiAccountEntry().getUbiSpaceId(),
                entity.getUbiAccountEntry().getUbiSessionId(),
                entity.getUbiAccountEntry().getUbiRememberDeviceTicket(),
                entity.getUbiAccountEntry().getUbiRememberMeTicket(),
                entity.getTradeByFiltersManagers().stream().map(tradeByFiltersManagerEntityMapper::createDTO).toList(),
                entity.getTradeByItemIdManagers().stream().map(tradeByItemIdManagerEntityMapper::createDTO).toList(),
                entity.getSellTradesManagingEnabledFlag(),
                entity.getSellTradePriorityExpression(),
                entity.getBuyTradesManagingEnabledFlag(),
                entity.getBuyTradePriorityExpression()
        );
    }
}
