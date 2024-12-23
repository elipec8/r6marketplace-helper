package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntry {
    private UbiAccountAuthorizationEntry ubiAccountAuthorizationEntry;
    private UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO;

    public String getEmail() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry == null ? null : ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
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

    public String getUbiProfileId() {
        return ubiAccountStatsEntityDTO == null ? null : ubiAccountStatsEntityDTO.getUbiProfileId();
    }

    public Integer getCreditAmount() {
        return ubiAccountStatsEntityDTO == null ? null : ubiAccountStatsEntityDTO.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStatsEntityDTO == null ? null : ubiAccountStatsEntityDTO.getOwnedItemsIds();
    }
}
