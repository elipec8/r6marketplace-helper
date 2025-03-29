package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PrioritizedUbiTradeTest {

    @Test
    public void constructor_should_properly_set_fields() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade(
                "tradeId",
                TradeState.Created,
                TradeCategory.Buy,
                LocalDateTime.of(2021, 1, 1, 0, 0),
                LocalDateTime.of(2022, 1, 1, 0, 0),
                new Item("itemId"),
                100,
                10,
                200,
                20,
                30,
                300L
        );

        UbiTrade ubiTrade = prioritizedUbiTrade.getUbiTrade();

        assertEquals("tradeId", ubiTrade.getTradeId());
        assertEquals(TradeState.Created, ubiTrade.getState());
        assertEquals(TradeCategory.Buy, ubiTrade.getCategory());
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), ubiTrade.getExpiresAt());
        assertEquals(LocalDateTime.of(2022, 1, 1, 0, 0), ubiTrade.getLastModifiedAt());
        assertEquals("itemId", ubiTrade.getItemId());
        assertEquals(100, ubiTrade.getSuccessPaymentPrice());
        assertEquals(10, ubiTrade.getSuccessPaymentFee());
        assertEquals(200, ubiTrade.getProposedPaymentPrice());
        assertEquals(20, ubiTrade.getProposedPaymentFee());
        assertEquals(30, prioritizedUbiTrade.getMinutesToTrade());
        assertEquals(300L, prioritizedUbiTrade.getTradePriority());
    }

    @Test
    public void getTradeId_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getTradeId());
    }

    @Test
    public void getTradeId_should_return_ubiTrade_tradeId() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setTradeId("tradeId");
        assertEquals("tradeId", prioritizedUbiTrade.getTradeId());
    }

    @Test
    public void getState_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getState());
    }

    @Test
    public void getState_should_return_ubiTrade_state() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setState(TradeState.Created);
        assertEquals(TradeState.Created, prioritizedUbiTrade.getState());
    }

    @Test
    public void getCategory_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getCategory());
    }

    @Test
    public void getCategory_should_return_ubiTrade_category() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setCategory(TradeCategory.Buy);
        assertEquals(TradeCategory.Buy, prioritizedUbiTrade.getCategory());
    }

    @Test
    public void getExpiresAt_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getExpiresAt());
    }

    @Test
    public void getExpiresAt_should_return_ubiTrade_expiresAt() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setExpiresAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), prioritizedUbiTrade.getExpiresAt());
    }

    @Test
    public void getLastModifiedAt_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getLastModifiedAt());
    }

    @Test
    public void getLastModifiedAt_should_return_ubiTrade_lastModifiedAt() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setLastModifiedAt(LocalDateTime.of(2021, 1, 1, 0, 0));
        assertEquals(LocalDateTime.of(2021, 1, 1, 0, 0), prioritizedUbiTrade.getLastModifiedAt());
    }

    @Test
    public void getItem_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getItem());
    }

    @Test
    public void getItem_should_return_ubiTrade_item() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setItem(new Item("itemId"));
        assertEquals("itemId", prioritizedUbiTrade.getItem().getItemId());
    }

    @Test
    public void getSuccessPaymentPrice_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getSuccessPaymentPrice());
    }

    @Test
    public void getSuccessPaymentPrice_should_return_ubiTrade_successPaymentPrice() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setSuccessPaymentPrice(100);
        assertEquals(100, prioritizedUbiTrade.getSuccessPaymentPrice());
    }

    @Test
    public void getSuccessPaymentFee_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getSuccessPaymentFee());
    }

    @Test
    public void getSuccessPaymentFee_should_return_ubiTrade_successPaymentFee() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setSuccessPaymentFee(10);
        assertEquals(10, prioritizedUbiTrade.getSuccessPaymentFee());
    }

    @Test
    public void getProposedPaymentPrice_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getProposedPaymentPrice());
    }

    @Test
    public void getProposedPaymentPrice_should_return_ubiTrade_proposedPaymentPrice() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setProposedPaymentPrice(200);
        assertEquals(200, prioritizedUbiTrade.getProposedPaymentPrice());
    }

    @Test
    public void getProposedPaymentFee_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getProposedPaymentFee());
    }

    @Test
    public void getProposedPaymentFee_should_return_ubiTrade_proposedPaymentFee() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setProposedPaymentFee(20);
        assertEquals(20, prioritizedUbiTrade.getProposedPaymentFee());
    }

    @Test
    public void setProposedPaymentPrice_should_set_ubiTrade_proposedPaymentPrice() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.setProposedPaymentPrice(200);
        assertEquals(200, prioritizedUbiTrade.getUbiTrade().getProposedPaymentPrice());
    }

    @Test
    public void setCategory_should_set_ubiTrade_category() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.setCategory(TradeCategory.Buy);
        assertEquals(TradeCategory.Buy, prioritizedUbiTrade.getUbiTrade().getCategory());
    }

    @Test
    public void setTradeId_should_set_ubiTrade_tradeId() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.setTradeId("tradeId");
        assertEquals("tradeId", prioritizedUbiTrade.getUbiTrade().getTradeId());
    }

    @Test
    public void setItem_should_set_ubiTrade_item() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.setItem(new Item("itemId"));
        assertEquals("itemId", prioritizedUbiTrade.getUbiTrade().getItemId());
    }

    @Test
    public void getItemId_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getItemId());
    }

    @Test
    public void getItemId_should_return_ubiTrade_itemId() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setItem(new Item("itemId"));
        assertEquals("itemId", prioritizedUbiTrade.getItemId());
    }

    @Test
    public void getItemName_should_return_null_if_null_ubiTrade() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertNull(prioritizedUbiTrade.getItemName());
    }

    @Test
    public void getItemName_should_return_ubiTrade_itemName() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        prioritizedUbiTrade.setUbiTrade(new UbiTrade());
        prioritizedUbiTrade.getUbiTrade().setItem(new Item());
        prioritizedUbiTrade.getUbiTrade().getItem().setName("itemName");
        assertEquals("itemName", prioritizedUbiTrade.getItemName());
    }

    @Test
    public void isFullyEqual_should_return_true_if_same() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();
        assertTrue(prioritizedUbiTrade.isFullyEqual(prioritizedUbiTrade));
    }

    @Test
    public void isFullyEqual_should_return_false_if_null() {
        PrioritizedUbiTrade prioritizedUbiTrade = new PrioritizedUbiTrade();

        assertFalse(prioritizedUbiTrade.isFullyEqual(null));
    }

    @Test
    public void isFullyEqual_should_return_true_if_equal_fields() {
        UbiTrade ubiTrade1 = new UbiTrade();
        ubiTrade1.setTradeId("tradeId1");
        ubiTrade1.setState(TradeState.Created);
        ubiTrade1.setCategory(TradeCategory.Buy);
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 1));
        ubiTrade1.setItem(new Item("itemId1"));
        ubiTrade1.setSuccessPaymentPrice(100);
        ubiTrade1.setSuccessPaymentFee(10);
        ubiTrade1.setProposedPaymentPrice(75);
        ubiTrade1.setProposedPaymentFee(7);

        PrioritizedUbiTrade prioritizedUbiTrade1 = new PrioritizedUbiTrade();
        prioritizedUbiTrade1.setUbiTrade(ubiTrade1);
        prioritizedUbiTrade1.setMinutesToTrade(15);
        prioritizedUbiTrade1.setTradePriority(16L);

        UbiTrade ubiTrade2 = new UbiTrade();
        ubiTrade2.setTradeId("tradeId1");
        ubiTrade2.setState(TradeState.Created);
        ubiTrade2.setCategory(TradeCategory.Buy);
        ubiTrade2.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade2.setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 1));
        ubiTrade2.setItem(new Item("itemId1"));
        ubiTrade2.setSuccessPaymentPrice(100);
        ubiTrade2.setSuccessPaymentFee(10);
        ubiTrade2.setProposedPaymentPrice(75);
        ubiTrade2.setProposedPaymentFee(7);

        PrioritizedUbiTrade prioritizedUbiTrade2 = new PrioritizedUbiTrade();
        prioritizedUbiTrade2.setUbiTrade(ubiTrade2);
        prioritizedUbiTrade2.setMinutesToTrade(15);
        prioritizedUbiTrade2.setTradePriority(16L);

        assertTrue(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
    }

    @Test
    public void isFullyEqual_should_return_false_if_different_fields() {
        UbiTrade ubiTrade1 = new UbiTrade();
        ubiTrade1.setTradeId("tradeId1");
        ubiTrade1.setState(TradeState.Created);
        ubiTrade1.setCategory(TradeCategory.Buy);
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 1));
        ubiTrade1.setItem(new Item("itemId1"));
        ubiTrade1.setSuccessPaymentPrice(100);
        ubiTrade1.setSuccessPaymentFee(10);
        ubiTrade1.setProposedPaymentPrice(75);
        ubiTrade1.setProposedPaymentFee(7);

        PrioritizedUbiTrade prioritizedUbiTrade1 = new PrioritizedUbiTrade();
        prioritizedUbiTrade1.setUbiTrade(ubiTrade1);
        prioritizedUbiTrade1.setMinutesToTrade(15);
        prioritizedUbiTrade1.setTradePriority(16L);

        UbiTrade ubiTrade2 = new UbiTrade();
        ubiTrade2.setTradeId("tradeId1");
        ubiTrade2.setState(TradeState.Created);
        ubiTrade2.setCategory(TradeCategory.Buy);
        ubiTrade2.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade2.setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 1));
        ubiTrade2.setItem(new Item("itemId1"));
        ubiTrade2.setSuccessPaymentPrice(100);
        ubiTrade2.setSuccessPaymentFee(10);
        ubiTrade2.setProposedPaymentPrice(75);
        ubiTrade2.setProposedPaymentFee(7);

        PrioritizedUbiTrade prioritizedUbiTrade2 = new PrioritizedUbiTrade();
        prioritizedUbiTrade2.setUbiTrade(ubiTrade2);
        prioritizedUbiTrade2.setMinutesToTrade(15);
        prioritizedUbiTrade2.setTradePriority(16L);

        prioritizedUbiTrade1.setTradeId("tradeId2");
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.setTradeId("tradeId1");
        prioritizedUbiTrade1.getUbiTrade().setState(TradeState.Failed);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setState(TradeState.Created);
        prioritizedUbiTrade1.getUbiTrade().setCategory(TradeCategory.Sell);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setCategory(TradeCategory.Buy);
        prioritizedUbiTrade1.getUbiTrade().setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 2));
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        prioritizedUbiTrade1.getUbiTrade().setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 2));
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setLastModifiedAt(LocalDateTime.of(2021, 2, 1, 1, 1));
        prioritizedUbiTrade1.getUbiTrade().setItem(new Item("itemId2"));
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setItem(new Item("itemId1"));
        prioritizedUbiTrade1.getUbiTrade().setSuccessPaymentPrice(101);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setSuccessPaymentPrice(100);
        prioritizedUbiTrade1.getUbiTrade().setSuccessPaymentFee(11);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setSuccessPaymentFee(10);
        prioritizedUbiTrade1.getUbiTrade().setProposedPaymentPrice(76);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setProposedPaymentPrice(75);
        prioritizedUbiTrade1.getUbiTrade().setProposedPaymentFee(8);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.getUbiTrade().setProposedPaymentFee(7);
        prioritizedUbiTrade1.setMinutesToTrade(16);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
        prioritizedUbiTrade1.setMinutesToTrade(15);
        prioritizedUbiTrade1.setTradePriority(17L);
        assertFalse(prioritizedUbiTrade1.isFullyEqual(prioritizedUbiTrade2));
    }
}