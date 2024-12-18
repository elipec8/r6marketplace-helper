package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalSellTrade extends PotentialTrade {
    public PotentialPersonalSellTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory() {
        return TradeCategory.Sell;
    }
}
