package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class UbiAccountStats {
    private String ubiProfileId;

    private Integer soldIn24h;

    private Integer boughtIn24h;

    private Integer creditAmount;

    private List<String> ownedItemsIds = new ArrayList<>();

    private List<ItemResaleLockWithUbiAccount> resaleLocks = new ArrayList<>();

    private List<UbiTrade> currentSellTrades = new ArrayList<>();

    private List<UbiTrade> currentBuyTrades = new ArrayList<>();

    public UbiAccountStats(String ubiProfileId, Integer soldIn24h, Integer boughtIn24h, Integer creditAmount, List<String> ownedItemsIds,
                           List<ItemResaleLockWithUbiAccount> resaleLocks, List<UbiTrade> currentSellTrades,
                           List<UbiTrade> currentBuyTrades) {
        this.ubiProfileId = ubiProfileId;
        this.soldIn24h = soldIn24h;
        this.boughtIn24h = boughtIn24h;
        this.creditAmount = creditAmount;
        this.ownedItemsIds = ownedItemsIds;
        this.resaleLocks = resaleLocks;
        this.currentSellTrades = currentSellTrades;
        this.currentBuyTrades = currentBuyTrades;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ubiProfileId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountStats ubiAccountStats)) {
            return false;
        }
        return Objects.equals(ubiAccountStats.ubiProfileId, ubiProfileId);
    }

    public boolean isFullyEqual(Object o) {
        if (this.equals(o) && o instanceof UbiAccountStats ubiAccountStats) {

            boolean ownedItemsIdsEqual = ownedItemsIds.size() == ubiAccountStats.ownedItemsIds.size() &&
                                         new HashSet<>(ownedItemsIds).containsAll(ubiAccountStats.ownedItemsIds);

            boolean resaleLocksEqual = resaleLocks.size() == ubiAccountStats.resaleLocks.size() &&
                                       new HashSet<>(resaleLocks).containsAll(ubiAccountStats.resaleLocks);

            boolean currentSellTradesEqual = currentSellTrades.size() == ubiAccountStats.currentSellTrades.size() &&
                                             new HashSet<>(currentSellTrades).containsAll(ubiAccountStats.currentSellTrades);

            boolean currentBuyTradesEqual = currentBuyTrades.size() == ubiAccountStats.currentBuyTrades.size() &&
                                            new HashSet<>(currentBuyTrades).containsAll(ubiAccountStats.currentBuyTrades);

            return Objects.equals(ubiProfileId, ubiAccountStats.ubiProfileId) &&
                   Objects.equals(soldIn24h, ubiAccountStats.soldIn24h) &&
                   Objects.equals(boughtIn24h, ubiAccountStats.boughtIn24h) &&
                   Objects.equals(creditAmount, ubiAccountStats.creditAmount) &&
                   ownedItemsIdsEqual &&
                   resaleLocksEqual &&
                   currentSellTradesEqual &&
                   currentBuyTradesEqual;
        } else {
            return false;
        }
    }
}
