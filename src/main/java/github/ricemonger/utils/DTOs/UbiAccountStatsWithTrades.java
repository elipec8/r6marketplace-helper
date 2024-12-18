package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UbiAccountStatsWithTrades extends UbiAccountStats {

    private List<UbiTrade> currentSellTrades = new ArrayList<>();

    private List<UbiTrade> currentBuyTrades = new ArrayList<>();

    public UbiAccountStatsWithTrades(String ubiProfileId, Integer soldIn24h, Integer boughtIn24h, Integer creditAmount, List<String> ownedItemsIds,
                                     List<ItemResaleLockWithUbiAccount> resaleLocks, List<UbiTrade> currentSellTrades,
                                     List<UbiTrade> currentBuyTrades) {
        super(ubiProfileId, soldIn24h, boughtIn24h, creditAmount, ownedItemsIds, resaleLocks);
        this.currentSellTrades = currentSellTrades;
        this.currentBuyTrades = currentBuyTrades;
    }

    public String toString() {
        return "UbiAccountStatsWithTrades(" + super.toString() + ", currentSellTrades=" + this.getCurrentSellTrades() + ", currentBuyTrades=" + this.getCurrentBuyTrades() + ")";
    }

    @Override
    public boolean isFullyEqual(Object o) {
        if (super.isFullyEqual(o) && o instanceof UbiAccountStatsWithTrades ubiAccountStatsWithTrades) {
            boolean currentSellTradesEqual = currentSellTrades.size() == ubiAccountStatsWithTrades.currentSellTrades.size() &&
                                             new HashSet<>(currentSellTrades).containsAll(ubiAccountStatsWithTrades.currentSellTrades);

            boolean currentBuyTradesEqual = currentBuyTrades.size() == ubiAccountStatsWithTrades.currentBuyTrades.size() &&
                                            new HashSet<>(currentBuyTrades).containsAll(ubiAccountStatsWithTrades.currentBuyTrades);

            return currentBuyTradesEqual && currentSellTradesEqual;
        } else {
            return false;
        }
    }
}
