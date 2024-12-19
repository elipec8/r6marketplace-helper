package github.ricemonger.utils.DTOs;

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
        return ubiAccountAuthorizationEntry.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntry.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
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

    public String getUbiProfileId() {
        return ubiAccountStatsEntityDTO.getUbiProfileId();
    }

    public Integer getCreditAmount() {
        return ubiAccountStatsEntityDTO.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStatsEntityDTO.getOwnedItemsIds();
    }
}
