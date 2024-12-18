package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountEntryEntityDTO {
    private UbiAccountAuthorizationEntryEntityDTO ubiAccountAuthorizationEntryEntityDTO;
    private UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO;

    public String getEmail() {
        return ubiAccountAuthorizationEntryEntityDTO.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountAuthorizationEntryEntityDTO.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountAuthorizationEntryEntityDTO.getUbiRememberMeTicket();
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
