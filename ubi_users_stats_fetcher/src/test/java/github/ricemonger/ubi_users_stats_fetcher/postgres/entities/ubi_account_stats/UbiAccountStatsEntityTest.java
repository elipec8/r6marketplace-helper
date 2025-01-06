package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountStatsEntityTest {
    @Test
    public void equals_should_return_true_for_same() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity("ubiProfileId");
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_for_equal_id_fields() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(2);
        entity1.setSoldIn24h(3);
        entity1.setBoughtIn24h(4);

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setSoldIn24h(2);
        entity2.setBoughtIn24h(3);

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity("ubiProfileId");
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId1");

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void isFullyEqual_should_return_true_for_same() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity("ubiProfileId");
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_for_equal_fields() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity(entity1, new ItemIdEntity("itemId"), LocalDateTime.of(2021, 1, 1, 0, 0))));
        UbiTradeEntity trade1 = new UbiTradeEntity();
        trade1.setTradeId("tradeId");
        trade1.setItem(new ItemIdEntity("itemId"));
        entity1.setCurrentSellTrades(List.of(trade1));
        entity1.setCurrentBuyTrades(List.of(trade1));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setSoldIn24h(2);
        entity2.setBoughtIn24h(3);
        entity2.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        entity2.setResaleLocks(List.of(new ItemResaleLockEntity(entity1, new ItemIdEntity("itemId"), LocalDateTime.of(2022, 1, 1, 0, 0))));
        UbiTradeEntity trade2 = new UbiTradeEntity();
        trade2.setTradeId("tradeId");
        trade2.setItem(new ItemIdEntity("itemId1"));
        entity2.setCurrentSellTrades(List.of(trade2));
        entity2.setCurrentBuyTrades(List.of(trade2));

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_false_for_null() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity("ubiProfileId");
        assertFalse(entity.isFullyEqual(null));
    }

    @Test
    public void isFullyEqual_should_return_false_for_different_fields() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity(entity1, new ItemIdEntity("itemId"), LocalDateTime.of(2021, 1, 1, 0, 0))));
        UbiTradeEntity trade11 = new UbiTradeEntity();
        trade11.setTradeId("tradeId");
        trade11.setItem(new ItemIdEntity("itemId"));
        UbiTradeEntity trade12 = new UbiTradeEntity();
        trade12.setTradeId("tradeId");
        trade12.setItem(new ItemIdEntity("itemId"));
        entity1.setCurrentSellTrades(List.of(trade11));
        entity1.setCurrentBuyTrades(List.of(trade12));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setSoldIn24h(2);
        entity2.setBoughtIn24h(3);
        entity2.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        entity2.setResaleLocks(List.of(new ItemResaleLockEntity(entity2, new ItemIdEntity("itemId"), LocalDateTime.of(2021, 1, 1, 0, 0))));
        UbiTradeEntity trade21 = new UbiTradeEntity();
        trade21.setTradeId("tradeId");
        trade21.setItem(new ItemIdEntity("itemId"));
        UbiTradeEntity trade22 = new UbiTradeEntity();
        trade22.setTradeId("tradeId1");
        trade22.setItem(new ItemIdEntity("itemId"));
        entity2.setCurrentSellTrades(List.of(trade21));
        entity2.setCurrentBuyTrades(List.of(trade22));

        entity1.setUbiProfileId("ubiProfileId1");
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(2);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(3);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(4);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity("itemId1")));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity(entity1, new ItemIdEntity("itemId1"), LocalDateTime.of(2021, 1, 1, 0, 0))));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity(entity1, new ItemIdEntity("itemId"), LocalDateTime.of(2021, 1, 1, 0, 0))));
        trade11.setTradeId("tradeId2");
        assertFalse(entity1.isFullyEqual(entity2));
        trade11.setTradeId("tradeId");
        trade12.setTradeId("tradeId2");
        assertFalse(entity1.isFullyEqual(entity2));
    }
}