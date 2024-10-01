package github.ricemonger.utils.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountTradingEntry {
    private String ubiProfileId;
    private String ubiSessionId;
    private String ubiSpaceId;
    private String ubiAuthTicket;
    private String ubiTwoFactorAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    private List<String> ownedItemsIds;

    private Integer soldIn24h;
    private Integer boughtIn24h;

    private List<ItemResaleLock> resaleLocks;
    private List<UbiTrade> currentBuyTrades;
    private List<UbiTrade> currentSellTrades;
    private List<UbiTrade> finishedTrades;
}
