package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PrioritizedTradeEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_ids() {
        PrioritizedTradeEntity entity1 = new PrioritizedTradeEntity();
        entity1.setTradeId("tradeId");
        entity1.setMinutesToTrade(10);
        entity1.setTradePriority(1L);

        PrioritizedTradeEntity entity2 = new PrioritizedTradeEntity();
        entity2.setTradeId("tradeId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        PrioritizedTradeEntity entity = new PrioritizedTradeEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        PrioritizedTradeEntity entity1 = new PrioritizedTradeEntity();
        entity1.setTradeId("tradeId");
        entity1.setMinutesToTrade(10);
        entity1.setTradePriority(1L);

        PrioritizedTradeEntity entity2 = new PrioritizedTradeEntity();
        entity2.setTradeId("tradeId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        PrioritizedTradeEntity entity = new PrioritizedTradeEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        PrioritizedTradeEntity entity1 = new PrioritizedTradeEntity();
        entity1.setTradeId("tradeId1");

        PrioritizedTradeEntity entity2 = new PrioritizedTradeEntity();
        entity2.setTradeId("tradeId2");

        assertNotEquals(entity1, entity2);
    }
}