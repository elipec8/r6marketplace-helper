package github.ricemonger.notifications_service.postgres.services;

import github.ricemonger.notifications_service.postgres.dto_projections.ToBeNotifiedUserProjection;
import github.ricemonger.notifications_service.services.DTOs.ToBeNotifiedUser;
import org.springframework.stereotype.Service;

@Service
public class UserEntityMapper {

    public ToBeNotifiedUser createToBeNotifiedUser(ToBeNotifiedUserProjection projection) {
        return new ToBeNotifiedUser(
                projection.getChatId(),
                projection.getPrivateNotificationsEnabledFlag(),
                projection.getPublicNotificationsEnabledFlag(),
                projection.getUbiStatsUpdatedNotificationsEnabledFlag(),
                projection.getTradeManagerNotificationsEnabledFlag(),
                projection.getAuthorizationNotificationsEnabledFlag()
        );
    }
}
