package github.ricemonger.marketplace.databases.postgres.entities.user;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeState;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UbiTradeEntityTest {
    @Test
    public void toUbiTrade_should_map_fields() {
        UbiTradeEntity entity = new UbiTradeEntity();
        entity.setTradeId("tradeId");
        entity.setItem(new ItemEntity("item1"));
        entity.setState(TradeState.Created);
        entity.setCategory(TradeCategory.Buy);
        entity.setExpiresAt(LocalDateTime.MAX);
        entity.setLastModifiedAt(LocalDateTime.MIN);
        entity.setSuccessPaymentPrice(100);
        entity.setSuccessPaymentFee(10);
        entity.setProposedPaymentPrice(90);
        entity.setProposedPaymentFee(9);

        UbiTrade actual = entity.toUbiTrade();

        assertEquals("tradeId", actual.getTradeId());
        assertEquals("item1", actual.getItem());
        assertEquals(TradeState.Created, actual.getState());
        assertEquals(TradeCategory.Buy, actual.getCategory());
        assertEquals(LocalDateTime.MAX, actual.getExpiresAt());
        assertEquals(LocalDateTime.MIN, actual.getLastModifiedAt());
        assertEquals(100, actual.getSuccessPaymentPrice());
        assertEquals(10, actual.getSuccessPaymentFee());
        assertEquals(90, actual.getProposedPaymentPrice());
        assertEquals(9, actual.getProposedPaymentFee());
    }
}