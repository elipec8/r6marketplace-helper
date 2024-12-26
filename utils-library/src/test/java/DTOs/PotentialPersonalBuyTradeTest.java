package DTOs;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.PersonalItem;
import github.ricemonger.utils.DTOs.personal.PotentialPersonalBuyTrade;
import github.ricemonger.utils.enums.TradeCategory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PotentialPersonalBuyTradeTest {

    @Test
    void getTradeCategory_should_return_buy() {
        PersonalItem personalItem = new PersonalItem();
        PotentialTradeStats potentialTradeStats = new PotentialTradeStats();
        PotentialPersonalBuyTrade potentialPersonalBuyTrade = new PotentialPersonalBuyTrade(personalItem, potentialTradeStats);
        assertEquals(TradeCategory.Buy, potentialPersonalBuyTrade.getTradeCategory());
    }

}