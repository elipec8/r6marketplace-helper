package DTOs.personal;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UbiTradeTest {

    @Test
    public void getItemId_should_return_item_itemId() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(new Item("itemId"));
        assertEquals("itemId", ubiTrade.getItemId());
    }

    @Test
    public void getItemId_should_return_null_if_item_is_null() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(null);
        assertNull(ubiTrade.getItemId());
    }

    @Test
    public void getItemName_should_return_item_itemName() {
        UbiTrade ubiTrade = new UbiTrade();
        Item item = new Item();
        item.setName("itemName");
        ubiTrade.setItem(item);
        assertEquals("itemName", ubiTrade.getItemName());
    }

    @Test
    public void getItemName_should_return_null_if_item_is_null() {
        UbiTrade ubiTrade = new UbiTrade();
        ubiTrade.setItem(null);
        assertNull(ubiTrade.getItemName());
    }

    @Test
    public void isFullyEqualExceptItem_should_return_true_if_all_fields_except_item_fields_are_equal() {
        UbiTrade ubiTrade1 = new UbiTrade();
        ubiTrade1.setTradeId("tradeId");
        ubiTrade1.setState(TradeState.Created);
        ubiTrade1.setCategory(TradeCategory.Buy);
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        ubiTrade1.setItem(new Item("itemId"));
        ubiTrade1.setSuccessPaymentPrice(100);
        ubiTrade1.setSuccessPaymentFee(10);
        ubiTrade1.setProposedPaymentPrice(200);
        ubiTrade1.setProposedPaymentFee(20);

        UbiTrade ubiTrade2 = new UbiTrade();
        ubiTrade2.setTradeId("tradeId");
        ubiTrade2.setState(TradeState.Created);
        ubiTrade2.setCategory(TradeCategory.Buy);
        ubiTrade2.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        ubiTrade2.setItem(new Item("itemId"));
        ubiTrade2.getItem().setName("itemName");
        ubiTrade2.setSuccessPaymentPrice(100);
        ubiTrade2.setSuccessPaymentFee(10);
        ubiTrade2.setProposedPaymentPrice(200);
        ubiTrade2.setProposedPaymentFee(20);

        assertTrue(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
    }

    @Test
    public void isFullyEqualExceptItem_should_return_false_if_not_all_fields_except_item_fields_are_equal() {
        UbiTrade ubiTrade1 = new UbiTrade();
        ubiTrade1.setTradeId("tradeId");
        ubiTrade1.setState(TradeState.Created);
        ubiTrade1.setCategory(TradeCategory.Buy);
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        ubiTrade1.setItem(new Item("itemId"));
        ubiTrade1.setSuccessPaymentPrice(100);
        ubiTrade1.setSuccessPaymentFee(10);
        ubiTrade1.setProposedPaymentPrice(200);
        ubiTrade1.setProposedPaymentFee(20);

        UbiTrade ubiTrade2 = new UbiTrade();
        ubiTrade2.setTradeId("tradeId");
        ubiTrade2.setState(TradeState.Created);
        ubiTrade2.setCategory(TradeCategory.Buy);
        ubiTrade2.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade2.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        ubiTrade2.setItem(new Item("itemId"));
        ubiTrade2.setSuccessPaymentPrice(100);
        ubiTrade2.setSuccessPaymentFee(10);
        ubiTrade2.setProposedPaymentPrice(200);
        ubiTrade2.setProposedPaymentFee(20);

        ubiTrade1.setTradeId("tradeId2");
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setTradeId("tradeId");
        ubiTrade1.setState(TradeState.Failed);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setState(TradeState.Created);
        ubiTrade1.setCategory(TradeCategory.Sell);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setCategory(TradeCategory.Buy);
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 2));
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setExpiresAt(LocalDateTime.of(2021, 1, 1, 1, 1));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 2));
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setLastModifiedAt(LocalDateTime.of(2022, 1, 1, 1, 1));
        ubiTrade1.setItem(new Item("itemId2"));
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setItem(new Item("itemId"));
        ubiTrade1.setSuccessPaymentPrice(101);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setSuccessPaymentPrice(100);
        ubiTrade1.setSuccessPaymentFee(11);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setSuccessPaymentFee(10);
        ubiTrade1.setProposedPaymentPrice(201);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
        ubiTrade1.setProposedPaymentPrice(200);
        ubiTrade1.setProposedPaymentFee(21);
        assertFalse(ubiTrade1.isFullyEqualExceptItem(ubiTrade2));
    }

    @Test
    public void isFullyEqualExceptItem_should_return_false_other_is_null() {
        UbiTrade ubiTrade1 = new UbiTrade();
        assertFalse(ubiTrade1.isFullyEqualExceptItem(null));
    }
}