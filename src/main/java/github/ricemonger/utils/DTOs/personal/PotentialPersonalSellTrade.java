package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalSellTrade extends PotentialTrade {
    public PotentialPersonalSellTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory() {
        return TradeCategory.Sell;
    }
}
