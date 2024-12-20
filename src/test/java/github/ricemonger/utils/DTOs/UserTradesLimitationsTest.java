package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utils.DTOs.personal.ItemResaleLockWithUbiAccount;
import github.ricemonger.utils.DTOs.personal.UserTradesLimitations;
import github.ricemonger.utils.DTOs.personal.UserTransactionsCount;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void getItemResaleLocksWithUbiAccount_should_map_and_return_resale_locks() {
        UserTradesLimitations userTradesLimitations = new UserTradesLimitations();
        userTradesLimitations.setUbiProfileId("ubiProfileId");
        userTradesLimitations.setResolvedBuyTransactionCount(0);
        userTradesLimitations.setActiveBuyTransactionCount(0);
        userTradesLimitations.setResolvedSellTransactionCount(0);
        userTradesLimitations.setActiveSellTransactionCount(0);
        List<ItemResaleLock> itemResaleLocks = List.of(new ItemResaleLock(
                "itemId1", LocalDateTime.of(2021, 1, 1, 0, 0)), new ItemResaleLock(
                "itemId2", LocalDateTime.of(2022, 1, 1, 0, 0)));

        userTradesLimitations.setResaleLocks(itemResaleLocks);

        List<ItemResaleLockWithUbiAccount> result = userTradesLimitations.getItemResaleLocksWithUbiAccount();

        assertEquals(2, result.size());
        assertTrue(result.contains(new ItemResaleLockWithUbiAccount("ubiProfileId", "itemId1", LocalDateTime.of(2021, 1, 1, 0, 0))));
        assertTrue(result.contains(new ItemResaleLockWithUbiAccount("ubiProfileId", "itemId2", LocalDateTime.of(2022, 1, 1, 0, 0))));
    }
}