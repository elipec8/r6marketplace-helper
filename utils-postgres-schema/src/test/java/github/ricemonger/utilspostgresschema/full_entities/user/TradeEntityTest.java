package github.ricemonger.utilspostgresschema.full_entities.user;

import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import github.ricemonger.utilspostgresschema.full_entities.item.ItemEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TradeEntityTest {

    @Test
    public void hashCode_should_return_equal_hash_for_equal_ids(){
        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId("1");
        tradeEntity1.setState(TradeState.Created);
        tradeEntity1.setCategory(TradeCategory.Sell);
        tradeEntity1.setExpiresAt(LocalDateTime.now());
        tradeEntity1.setLastModifiedAt(LocalDateTime.now());
        tradeEntity1.setItem(new ItemEntity());
        tradeEntity1.getItem().setItemId("1");
        tradeEntity1.setSuccessPaymentPrice(1);
        tradeEntity1.setSuccessPaymentFee(1);
        tradeEntity1.setProposedPaymentPrice(1);
        tradeEntity1.setProposedPaymentFee(1);
        tradeEntity1.setMinutesToTrade(1);
        tradeEntity1.setTradePriority(1L);
        TradeEntity tradeEntity2 = new TradeEntity();
        tradeEntity2.setTradeId("1");

        assertEquals(tradeEntity1.hashCode(), tradeEntity2.hashCode());
    }

    @Test
    public void equals_should_return_true_for_same(){
        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId("1");

        assertEquals(tradeEntity1, tradeEntity1);
    }

    @Test
    public void equals_should_return_true_for_same_ids(){
        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId("1");
        tradeEntity1.setState(TradeState.Created);
        tradeEntity1.setCategory(TradeCategory.Sell);
        tradeEntity1.setExpiresAt(LocalDateTime.now());
        tradeEntity1.setLastModifiedAt(LocalDateTime.now());
        tradeEntity1.setItem(new ItemEntity());
        tradeEntity1.getItem().setItemId("1");
        tradeEntity1.setSuccessPaymentPrice(1);
        tradeEntity1.setSuccessPaymentFee(1);
        tradeEntity1.setProposedPaymentPrice(1);
        tradeEntity1.setProposedPaymentFee(1);
        tradeEntity1.setMinutesToTrade(1);
        tradeEntity1.setTradePriority(1L);
        TradeEntity tradeEntity2 = new TradeEntity();
        tradeEntity2.setTradeId("1");

        assertEquals(tradeEntity1, tradeEntity2);
    }

    @Test
    public void equals_should_return_false_for_different_ids(){
        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId("1");
        TradeEntity tradeEntity2 = new TradeEntity();
        tradeEntity2.setTradeId("2");

        assertNotEquals(tradeEntity1, tradeEntity2);
    }

    @Test
    public void equals_should_return_false_for_null(){
        TradeEntity tradeEntity1 = new TradeEntity();
        tradeEntity1.setTradeId("1");

        assertNotEquals(null, tradeEntity1);
    }
}