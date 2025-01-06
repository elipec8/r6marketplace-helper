package github.ricemonger.ubi_users_stats_fetcher.postgres.entities.ubi_account_stats;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UbiTradeEntityTest {

    @Test
    public void equals_should_return_true_if_same() {
        UbiTradeEntity entity = new UbiTradeEntity();
        assertTrue(entity.equals(entity));
    }

    @Test
    public void equals_should_return_true_if_equal_ids_different_fields() {
        UbiTradeEntity entity1 = new UbiTradeEntity();
        entity1.setTradeId("tradeId");
        entity1.setState(TradeState.Created);
        entity1.setCategory(TradeCategory.Sell);
        entity1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        entity1.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 0, 0));
        entity1.setSuccessPaymentPrice(1);
        entity1.setSuccessPaymentFee(2);
        entity1.setProposedPaymentPrice(3);
        entity1.setProposedPaymentFee(4);

        UbiTradeEntity entity2 = new UbiTradeEntity();
        entity2.setTradeId("tradeId");

        assertTrue(entity1.equals(entity2));
    }

    @Test
    public void equals_should_return_false_for_null() {
        UbiTradeEntity entity = new UbiTradeEntity();
        assertFalse(entity.equals(null));
    }

    @Test
    public void equals_should_return_false_for_different_ids() {
        UbiTradeEntity entity1 = new UbiTradeEntity();
        entity1.setTradeId("tradeId");

        UbiTradeEntity entity2 = new UbiTradeEntity();
        entity2.setTradeId("tradeId1");

        assertFalse(entity1.equals(entity2));
    }
}