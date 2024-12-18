package github.ricemonger.utils.DTOs;

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

    public UbiAccountAuthorizationEntryWithTelegram(UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry) {
        this.ubiAccountAuthorizationEntry = ubiAccountAuthorizationEntry;
    }

    public String getEmail() {
        return ubiAccountAuthorizationEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public String getUbiProfileId() {
        return ubiAccountAuthorizationEntry.getUbiProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationEntry.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationEntry.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationEntry.getUbiAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationEntry.getUbiRememberMeTicket();
    }
}
