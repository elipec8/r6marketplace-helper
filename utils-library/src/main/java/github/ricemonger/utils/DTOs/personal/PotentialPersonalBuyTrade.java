package github.ricemonger.utils.DTOs.personal;

import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalBuyTrade extends PotentialTrade {
    public PotentialPersonalBuyTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory() {
        return TradeCategory.Buy;
    }
}
