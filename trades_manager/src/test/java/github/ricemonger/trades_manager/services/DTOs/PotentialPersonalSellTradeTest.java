package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PotentialPersonalSellTradeTest {

    @Test
    public void getTradeCategory_should_return_sell() {
        PersonalItem personalItem = new PersonalItem();
        PotentialTradeStats potentialTradeStats = new PotentialTradeStats();
        PotentialPersonalSellTrade potentialPersonalSellTrade = new PotentialPersonalSellTrade(personalItem, potentialTradeStats);
        assertEquals(TradeCategory.Sell, potentialPersonalSellTrade.getTradeCategory());
    }
}