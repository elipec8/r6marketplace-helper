package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountAuthorizationEntryWithTelegram {
    private String chatId;

    private Boolean privateNotificationsEnabledFlag;

    private UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry;

    public String getEmail() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public String getUbiProfileId() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getUbiRememberMeTicket();
    }
}
