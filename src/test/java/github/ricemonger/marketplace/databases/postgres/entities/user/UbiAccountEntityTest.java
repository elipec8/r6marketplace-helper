package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.UbiAccountStats;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UbiAccountEntityTest {
    @Test
    public void toUbiAccountStats_should_return_correct_dto() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity("profile1");
        entity.setSoldIn24h(5);
        entity.setBoughtIn24h(3);

        ItemEntity item1 = new ItemEntity();
        item1.setItemId("item1");
        ItemEntity item2 = new ItemEntity();
        item2.setItemId("item2");
        entity.setOwnedItems(Arrays.asList(item1, item2));

        ItemResaleLockEntity lock1 = new ItemResaleLockEntity(entity, item1, LocalDateTime.now().withNano(0));
        ItemResaleLockEntity lock2 = new ItemResaleLockEntity(entity, item2, LocalDateTime.now().withNano(0).plusDays(1));
        entity.setResaleLocks(Arrays.asList(lock1, lock2));

        UbiTradeEntity buyTrade1 = new UbiTradeEntity();
        buyTrade1.setTradeId("trade1b");
        buyTrade1.setItem(item1);
        entity.setCurrentBuyTrades(List.of(buyTrade1));

        UbiTradeEntity sellTrade1 = new UbiTradeEntity();
        sellTrade1.setTradeId("trade1s");
        sellTrade1.setItem(item1);
        UbiTradeEntity sellTrade2 = new UbiTradeEntity();
        sellTrade2.setTradeId("trade2s");
        sellTrade2.setItem(item2);
        entity.setCurrentSellTrades(Arrays.asList(sellTrade1, sellTrade2));

        UbiTradeEntity finishedTrade1 = new UbiTradeEntity();
        finishedTrade1.setTradeId("trade1f");
        finishedTrade1.setItem(item1);
        UbiTradeEntity finishedTrade2 = new UbiTradeEntity();
        finishedTrade2.setTradeId("trade2f");
        finishedTrade2.setItem(item2);
        UbiTradeEntity finishedTrade3 = new UbiTradeEntity();
        finishedTrade3.setTradeId("trade3f");
        finishedTrade3.setItem(item1);

        UbiAccountStats stats = entity.toUbiAccountStats();

        assertEquals("profile1", stats.getUbiProfileId());
        assertEquals(Arrays.asList("item1", "item2"), stats.getOwnedItemsIds());
        assertEquals(5, stats.getSoldIn24h());
        assertEquals(3, stats.getBoughtIn24h());
        assertEquals(2, stats.getResaleLocks().size());
        assertEquals(1, stats.getCurrentBuyTrades().size());
        assertEquals(2, stats.getCurrentSellTrades().size());
    }
}