package github.ricemonger.marketplace.services.central_trade_manager;

import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalBuyTrade extends PotentialTrade {
    public PotentialPersonalBuyTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory(){
        return TradeCategory.Buy;
    }
}
