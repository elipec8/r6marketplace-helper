package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountWithTelegram {
    private String chatId;

    private UbiAccount ubiAccount;

    public String getEmail() {
        return ubiAccount.getEmail();
    }

    public void setEmail(String email) {
        ubiAccount.setEmail(email);
    }

    public String getEncodedPassword() {
        return ubiAccount.getEncodedPassword();
    }

    public void setEncodedPassword(String encodedPassword) {
        ubiAccount.setEncodedPassword(encodedPassword);
    }

    public String getUbiProfileId() {
        return ubiAccount.getUbiProfileId();
    }

    public void setUbiProfileId(String ubiProfileId) {
        ubiAccount.setUbiProfileId(ubiProfileId);
    }

    public String getUbiSessionId() {
        return ubiAccount.getUbiSessionId();
    }

    public void setUbiSessionId(String ubiSessionId) {
        ubiAccount.setUbiSessionId(ubiSessionId);
    }

    public String getUbiSpaceId() {
        return ubiAccount.getUbiSpaceId();
    }

    public void setUbiSpaceId(String ubiSpaceId) {
        ubiAccount.setUbiSpaceId(ubiSpaceId);
    }

    public String getUbiAuthTicket() {
        return ubiAccount.getUbiAuthTicket();
    }

    public void setUbiAuthTicket(String ubiAuthTicket) {
        ubiAccount.setUbiAuthTicket(ubiAuthTicket);
    }

    public String getUbiTwoFactorAuthTicket() {
        return ubiAccount.getUbiTwoFactorAuthTicket();
    }

    public void setUbiTwoFactorAuthTicket(String ubiTwoFactorAuthTicket) {
        ubiAccount.setUbiTwoFactorAuthTicket(ubiTwoFactorAuthTicket);
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccount.getUbiRememberDeviceTicket();
    }

    public void setUbiRememberDeviceTicket(String ubiRememberDeviceTicket) {
        ubiAccount.setUbiRememberDeviceTicket(ubiRememberDeviceTicket);
    }

    public String getUbiRememberMeTicket() {
        return ubiAccount.getUbiRememberMeTicket();
    }

    public void setUbiRememberMeTicket(String ubiRememberMeTicket) {
        ubiAccount.setUbiRememberMeTicket(ubiRememberMeTicket);
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccount.getOwnedItemsIds();
    }

    public void setOwnedItemsIds(List<String> ownedItemsIds) {
        ubiAccount.setOwnedItemsIds(ownedItemsIds);
    }
}
