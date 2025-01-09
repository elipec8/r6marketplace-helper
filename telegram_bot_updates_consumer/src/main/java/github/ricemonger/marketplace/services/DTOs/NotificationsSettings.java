package github.ricemonger.marketplace.services.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationsSettings {
    private Boolean publicNotificationsEnabledFlag;
    private Boolean privateNotificationsEnabledFlag;
}
