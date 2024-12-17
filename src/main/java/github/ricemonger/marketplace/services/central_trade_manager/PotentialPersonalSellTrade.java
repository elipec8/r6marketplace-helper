package github.ricemonger.marketplace.services.central_trade_manager;

import github.ricemonger.utils.enums.TradeCategory;

public class PotentialPersonalSellTrade extends PotentialTrade {
    public PotentialPersonalSellTrade(PersonalItem personalItem, PotentialTradeStats potentialTradeStats) {
        super(personalItem, potentialTradeStats);
    }

    public TradeCategory getTradeCategory() {
        return TradeCategory.Sell;
    }
}
