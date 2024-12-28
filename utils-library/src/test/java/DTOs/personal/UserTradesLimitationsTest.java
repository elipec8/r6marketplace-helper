package DTOs.personal;

import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.UserTransactionsCount;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTradesLimitationsTest {

    @Test
    public void setUserTransactionsCount_should_set_fields() {
        UserTradesLimitations userTradesLimitations = new UserTradesLimitations();
        userTradesLimitations.setUbiProfileId("ubiProfileId");
        userTradesLimitations.setResolvedBuyTransactionCount(0);
        userTradesLimitations.setActiveBuyTransactionCount(0);
        userTradesLimitations.setResolvedSellTransactionCount(0);
        userTradesLimitations.setActiveSellTransactionCount(0);
        userTradesLimitations.setResaleLocks(List.of());
        UserTransactionsCount userTransactionsCount = new UserTransactionsCount();
        userTransactionsCount.setBuyResolvedTransactionCount(1);
        userTransactionsCount.setBuyActiveTransactionCount(2);
        userTransactionsCount.setSellResolvedTransactionCount(3);
        userTransactionsCount.setSellActiveTransactionCount(4);

        userTradesLimitations.setUserTransactionsCount(userTransactionsCount);

        assertEquals("ubiProfileId", userTradesLimitations.getUbiProfileId());
        assertEquals(1, userTradesLimitations.getResolvedBuyTransactionCount());
        assertEquals(2, userTradesLimitations.getActiveBuyTransactionCount());
        assertEquals(3, userTradesLimitations.getResolvedSellTransactionCount());
        assertEquals(4, userTradesLimitations.getActiveSellTransactionCount());
        assertEquals(0, userTradesLimitations.getResaleLocks().size());
    }
}