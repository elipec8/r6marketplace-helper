package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UbiAccountStats {
    private String ubiProfileId;

    private Integer soldIn24h;
    private Integer boughtIn24h;
    private Integer creditAmount;

    private List<String> ownedItemsIds = new ArrayList<>();
    private List<ItemResaleLockWithUbiAccount> resaleLocks = new ArrayList<>();

    public UbiAccountStats(String ubiProfileId) {
        this.ubiProfileId = ubiProfileId;
    }

    public int hashCode() {
        return ubiProfileId.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof UbiAccountStats ubiAccountStats)) {
            return false;
        }
        return ubiAccountStats.ubiProfileId.equals(ubiProfileId);
    }

    public boolean isFullyEqual(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof UbiAccountStats ubiAccountStats)) {
            return false;
        }

        boolean ownedItemsIdsEqual = ownedItemsIds.size() == ubiAccountStats.ownedItemsIds.size() &&
                                     new HashSet<>(ownedItemsIds).containsAll(ubiAccountStats.ownedItemsIds);

        boolean resaleLocksEqual = resaleLocks.size() == ubiAccountStats.resaleLocks.size() &&
                                   new HashSet<>(resaleLocks).containsAll(ubiAccountStats.resaleLocks);

        return ubiAccountStats.ubiProfileId.equals(ubiProfileId) &&
               ubiAccountStats.soldIn24h.equals(soldIn24h) &&
               ubiAccountStats.boughtIn24h.equals(boughtIn24h) &&
               ubiAccountStats.creditAmount.equals(creditAmount) &&
               ownedItemsIdsEqual &&
               resaleLocksEqual;
    }
}
