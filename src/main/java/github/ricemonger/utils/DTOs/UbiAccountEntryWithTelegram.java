package github.ricemonger.utils.DTOs;

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

    private UbiAccountEntry ubiAccountEntryEntityDTO;

    public String getEmail() {
        return ubiAccountEntryEntityDTO.getEmail();
    }

    public String getEncodedPassword() {
        return ubiAccountEntryEntityDTO.getEncodedPassword();
    }

    public String getAuthorizationEntryProfileId() {
        return ubiAccountEntryEntityDTO.getAuthorizationEntryProfileId();
    }

    public String getUbiSessionId() {
        return ubiAccountEntryEntityDTO.getUbiSessionId();
    }

    public String getUbiSpaceId() {
        return ubiAccountEntryEntityDTO.getUbiSpaceId();
    }

    public String getUbiAuthTicket() {
        return ubiAccountEntryEntityDTO.getUbiAuthTicket();
    }

    public String getUbiRememberDeviceTicket() {
        return ubiAccountEntryEntityDTO.getUbiRememberDeviceTicket();
    }

    public String getUbiRememberMeTicket() {
        return ubiAccountEntryEntityDTO.getUbiRememberMeTicket();
    }

    public String getUbiProfileId() {
        return ubiAccountEntryEntityDTO.getUbiProfileId();
    }

    public Integer getCreditAmount() {
        return ubiAccountEntryEntityDTO.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountEntryEntityDTO.getOwnedItemsIds();
    }
}
