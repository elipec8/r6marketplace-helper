package github.ricemonger.trades_manager.postgres.entities.manageable_users;

import github.ricemonger.trades_manager.postgres.entities.items.ItemIdEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UbiAccountStatsEntityTest {

    @Test
    public void equals_should_return_false_if_null() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_true_if_same_object() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_ubiProfileId() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity()));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_not_equal_ubiProfileId() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId2");

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void isFullyEqual_should_return_false_if_null() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertFalse(entity.isFullyEqual(null));
    }

    @Test
    public void isFullyEqual_should_return_true_if_same_object() {
        UbiAccountStatsEntity entity = new UbiAccountStatsEntity();
        assertTrue(entity.isFullyEqual(entity));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal_ubiProfileId_and_creditAmount() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity()));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setSoldIn24h(2);
        entity2.setBoughtIn24h(3);
        entity2.setOwnedItems(List.of(new ItemIdEntity()));
        entity2.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity2.setCurrentSellTrades(List.of(new TradeEntity()));
        entity2.setCurrentBuyTrades(List.of(new TradeEntity()));

        assertTrue(entity1.isFullyEqual(entity2));
    }

    @Test
    public void isFullyEqual_should_return_true_if_different_field() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemIdEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity()));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity("ubiProfileId");
        entity2.setCreditAmount(1);
        entity2.setSoldIn24h(2);
        entity2.setBoughtIn24h(3);
        entity2.setOwnedItems(List.of(new ItemIdEntity()));
        entity2.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity2.setCurrentSellTrades(List.of(new TradeEntity()));
        entity2.setCurrentBuyTrades(List.of(new TradeEntity()));

        entity1.setUbiProfileId("ubiProfileId2");
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
        entity1.setOwnedItems(List.of(new ItemIdEntity(), new ItemIdEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(List.of(new ItemIdEntity("itemId")));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setOwnedItems(List.of(new ItemIdEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity(), new ItemResaleLockEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity(), new TradeEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCurrentSellTrades(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCurrentSellTrades(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity(), new TradeEntity()));
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCurrentBuyTrades(null);
        assertFalse(entity1.isFullyEqual(entity2));
        entity1.setCurrentBuyTrades(List.of());
        assertFalse(entity1.isFullyEqual(entity2));
    }
}