package github.ricemonger.utils.DTOs.personal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTradesLimitations {
    private String ubiProfileId;

    private Integer resolvedBuyTransactionCount;
    private Integer activeBuyTransactionCount;

    private Integer resolvedSellTransactionCount;
    private Integer activeSellTransactionCount;

    private List<ItemResaleLock> resaleLocks = new ArrayList<>();

    public void setUserTransactionsCount(UserTransactionsCount userTransactionsCount) {
        this.resolvedBuyTransactionCount = userTransactionsCount.getBuyResolvedTransactionCount();
        this.activeBuyTransactionCount = userTransactionsCount.getBuyActiveTransactionCount();
        this.resolvedSellTransactionCount = userTransactionsCount.getSellResolvedTransactionCount();
        this.activeSellTransactionCount = userTransactionsCount.getSellActiveTransactionCount();
    }

    public List<ItemResaleLockWithUbiAccount> getItemResaleLocksWithUbiAccount() {
        return resaleLocks.stream().map(itemResaleLock -> new ItemResaleLockWithUbiAccount(ubiProfileId, itemResaleLock)).toList();
    }
}
