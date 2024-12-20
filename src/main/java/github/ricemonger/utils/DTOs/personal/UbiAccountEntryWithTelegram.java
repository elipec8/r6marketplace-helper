package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryWithTelegram {
    private String chatId;

    private Boolean privateNotificationsEnabledFlag;

    private UbiAccountEntry ubiAccountEntry;

    public String getEmail() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getAuthorizationEntryProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiRememberMeTicket();
    }

    public String getUbiProfileId() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getUbiProfileId();
    }

    public Integer getCreditAmount() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountEntry == null ? null : ubiAccountEntry.getOwnedItemsIds();
    }
}
