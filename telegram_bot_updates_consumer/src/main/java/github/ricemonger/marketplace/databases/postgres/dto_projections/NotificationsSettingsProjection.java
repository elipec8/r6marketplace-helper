package github.ricemonger.marketplace.databases.postgres.dto_projections;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsSettingsProjection {
    private Boolean publicNotificationsEnabledFlag;
    private Boolean privateNotificationsEnabledFlag;
    private Boolean ubiStatsUpdatedNotificationsEnabledFlag;
    private Boolean tradeManagerNotificationsEnabledFlag;
    private Boolean authorizationNotificationsEnabledFlag;
}
