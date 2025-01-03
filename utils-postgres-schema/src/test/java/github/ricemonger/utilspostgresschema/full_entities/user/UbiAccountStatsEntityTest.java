package github.ricemonger.utilspostgresschema.full_entities.user;


import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiAccountStatsEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity()));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_same() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();

        assertEquals(entity1, entity1);
    }

    @Test
    public void equals_should_return_true_for_same_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");
        entity1.setCreditAmount(1);
        entity1.setSoldIn24h(2);
        entity1.setBoughtIn24h(3);
        entity1.setOwnedItems(List.of(new ItemEntity()));
        entity1.setResaleLocks(List.of(new ItemResaleLockEntity()));
        entity1.setCurrentSellTrades(List.of(new TradeEntity()));
        entity1.setCurrentBuyTrades(List.of(new TradeEntity()));

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();
        entity1.setUbiProfileId("ubiProfileId");

        UbiAccountStatsEntity entity2 = new UbiAccountStatsEntity();
        entity2.setUbiProfileId("ubiProfileId1");

        assertNotEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiAccountStatsEntity entity1 = new UbiAccountStatsEntity();

        assertNotEquals(null, entity1);
    }
}