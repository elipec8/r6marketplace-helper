package github.ricemonger.notifications_service.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToBeNotifiedUserProjection {
    private String chatId;
    private Boolean privateNotificationsEnabledFlag;
    private Boolean publicNotificationsEnabledFlag;
    private Boolean ubiStatsUpdatedNotificationsEnabledFlag;
    private Boolean tradeManagerNotificationsEnabledFlag;
    private Boolean authorizationNotificationsEnabledFlag;
}
