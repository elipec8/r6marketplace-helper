package github.ricemonger.trades_manager.postgres.services.entity_mappers.user;

import github.ricemonger.trades_manager.postgres.entities.manageable_users.ManageableUserEntity;
import github.ricemonger.utils.DTOs.personal.ManageableUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    private final TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    public ManageableUser createManageableUser(ManageableUserEntity entity) {
        return new ManageableUser(
                entity.getId(),
                entity.getUbiAccountEntry().getProfileId_(),
                entity.getUbiAccountEntry().getUbiSessionId(),
                entity.getUbiAccountEntry().getUbiSpaceId(),
                entity.getUbiAccountEntry().getUbiAuthTicket(),
                entity.getUbiAccountEntry().getUbiRememberDeviceTicket(),
                entity.getUbiAccountEntry().getUbiRememberMeTicket(),
                entity.getTradeByFiltersManagers().stream().map(tradeByFiltersManagerEntityMapper::createDTO).toList(),
                entity.getTradeByItemIdManagers().stream().map(tradeByItemIdManagerEntityMapper::createDTO).toList()
        );
    }
}
