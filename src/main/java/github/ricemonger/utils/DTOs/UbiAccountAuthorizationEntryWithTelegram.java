package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountAuthorizationEntryWithTelegram {
    private String chatId;

    private UbiAccountAuthorizationDTO ubiAccountEntry;

    public String getEmail() {
        return ubiAccountEntry.getEmail();
    }

    public void setEmail(String email) {
        ubiAccountEntry.setEmail(email);
    }

    public String getEncodedPassword() {
        return ubiAccountEntry.getEncodedPassword();
    }

    public void setEncodedPassword(String encodedPassword) {
        ubiAccountEntry.setEncodedPassword(encodedPassword);
    }

    public String getUbiProfileId() {
        return ubiAccountEntry.getUbiProfileId();
    }

    public void setUbiProfileId(String ubiProfileId) {
        ubiAccountEntry.setUbiProfileId(ubiProfileId);
    }

    public String getUbiSessionId() {
        return ubiAccountEntry.getUbiSessionId();
    }

    public void setUbiSessionId(String ubiSessionId) {
        ubiAccountEntry.setUbiSessionId(ubiSessionId);
    }

    public String getUbiSpaceId() {
        return ubiAccountEntry.getUbiSpaceId();
    }

    public void setUbiSpaceId(String ubiSpaceId) {
        ubiAccountEntry.setUbiSpaceId(ubiSpaceId);
    }

    public String getUbiAuthTicket() {
        return ubiAccountEntry.getUbiAuthTicket();
    }

    public void setUbiAuthTicket(String ubiAuthTicket) {
        ubiAccountEntry.setUbiAuthTicket(ubiAuthTicket);
    }

    public String getUbiTwoFactorAuthTicket() {
        return ubiAccountEntry.getUbiTwoFactorAuthTicket();
    }

    public void setUbiTwoFactorAuthTicket(String ubiTwoFactorAuthTicket) {
        ubiAccountEntry.setUbiTwoFactorAuthTicket(ubiTwoFactorAuthTicket);
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountEntry.getUbiRememberDeviceTicket();
    }

    public void setUbiRememberDeviceTicket(String ubiRememberDeviceTicket) {
        ubiAccountEntry.setUbiRememberDeviceTicket(ubiRememberDeviceTicket);
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountEntry.getUbiRememberMeTicket();
    }

    public void setUbiRememberMeTicket(String ubiRememberMeTicket) {
        ubiAccountEntry.setUbiRememberMeTicket(ubiRememberMeTicket);
    }
}
