package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.PotentialTradeStats;
import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalBuyTrade extends PotentialTrade {
    public PotentialPersonalBuyTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory(){
        return TradeCategory.Buy;
    }
}
