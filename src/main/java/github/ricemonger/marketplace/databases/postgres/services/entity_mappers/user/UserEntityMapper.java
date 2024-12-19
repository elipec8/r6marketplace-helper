package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user;

import github.ricemonger.marketplace.databases.postgres.entities.user.UserEntity;
import github.ricemonger.utils.DTOs.ManageableUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEntityMapper {

    private final UbiAccountStatsEntityMapper ubiAccountStatsEntityMapper;

    private final TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    private final TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    public ManageableUser createManageableUser(UserEntity entity) {
        return new ManageableUser(
                entity.getId(),
                ubiAccountStatsEntityMapper.createDTO(entity.getUbiAccountAuthorizationEntry().getUbiAccountStats()),
                entity.getUbiAccountAuthorizationEntry().getUbiSessionId(),
                entity.getUbiAccountAuthorizationEntry().getUbiSpaceId(),
                entity.getUbiAccountAuthorizationEntry().getUbiAuthTicket(),
                entity.getUbiAccountAuthorizationEntry().getUbiRememberDeviceTicket(),
                entity.getUbiAccountAuthorizationEntry().getUbiRememberMeTicket(),
                entity.getTelegramUser().getChatId(),
                entity.getPrivateNotificationsEnabledFlag(),
                entity.getTradeByFiltersManagers().stream().map(tradeByFiltersManagerEntityMapper::createDTO).toList(),
                entity.getTradeByItemIdManagers().stream().map(tradeByItemIdManagerEntityMapper::createDTO).toList()
        );
    }
}
