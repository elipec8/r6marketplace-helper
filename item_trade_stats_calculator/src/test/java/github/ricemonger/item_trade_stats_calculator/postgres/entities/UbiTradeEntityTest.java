package github.ricemonger.item_trade_stats_calculator.postgres.entities;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UbiTradeEntityTest {

    @Test
    public void hashCode_should_return_same_hash_for_equal_ids() {
        UbiTradeEntity entity1 = new UbiTradeEntity();
        entity1.setTradeId("tradeId");
        entity1.setCategory(TradeCategory.Sell);
        entity1.setState(TradeState.Created);
        entity1.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        entity1.setLastModifiedAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        entity1.setProposedPaymentPrice(1);
        entity1.setProposedPaymentFee(2);
        entity1.setSuccessPaymentPrice(3);
        entity1.setSuccessPaymentFee(4);

        UbiTradeEntity entity2 = new UbiTradeEntity();
        entity2.setTradeId("tradeId");

        assertEquals(entity1.hashCode(), entity2.hashCode());
    }

    @Test
    public void equals_should_return_true_if_same() {
        UbiTradeEntity entity = new UbiTradeEntity();
        assertEquals(entity, entity);
    }

    @Test
    public void equals_should_return_true_if_equal_id() {
        UbiTradeEntity entity1 = new UbiTradeEntity();
        entity1.setTradeId("tradeId");
        entity1.setCategory(TradeCategory.Sell);
        entity1.setState(TradeState.Created);
        entity1.setExpiresAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        entity1.setLastModifiedAt(LocalDateTime.of(2023, 1, 1, 1, 1));
        entity1.setProposedPaymentPrice(1);
        entity1.setProposedPaymentFee(2);
        entity1.setSuccessPaymentPrice(3);
        entity1.setSuccessPaymentFee(4);

        UbiTradeEntity entity2 = new UbiTradeEntity();
        entity2.setTradeId("tradeId");

        assertEquals(entity1, entity2);
    }

    @Test
    public void equals_should_return_false_if_null() {
        UbiTradeEntity entity = new UbiTradeEntity();
        assertNotEquals(null, entity);
    }

    @Test
    public void equals_should_return_false_if_different_ids() {
        UbiTradeEntity entity1 = new UbiTradeEntity();
        entity1.setTradeId("tradeId1");

        UbiTradeEntity entity2 = new UbiTradeEntity();
        entity2.setTradeId("tradeId2");

        assertNotEquals(entity1, entity2);
    }
}