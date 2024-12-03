package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStats {
    private String ubiProfileId;

    private Integer soldIn24h;
    private Integer boughtIn24h;

    private List<String> ownedItemsIds;
    private List<ItemResaleLockWithUbiAccount> resaleLocks;
    private List<UbiTrade> currentBuyTrades;
    private List<UbiTrade> currentSellTrades;
    private List<UbiTrade> finishedTrades;
}
