package github.ricemonger.utils.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManageableUser {
    private Long id;

    private UbiAccountStatsEntityDTO ubiAccountStatsEntityDTO;

    private String ubiSessionId;
    private String ubiSpaceId;
    private String ubiAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    private String chatId;
    private Boolean privateNotificationsEnabledFlag;

    private List<TradeByFiltersManager> tradeByFiltersManagers = new ArrayList<>();

    private List<TradeByItemIdManager> tradeByItemIdManagers = new ArrayList<>();

    public String getUbiProfileId() {
        if (ubiAccountStatsEntityDTO == null) {
            return null;
        } else {
            return ubiAccountStatsEntityDTO.getUbiProfileId();
        }
    }
}
