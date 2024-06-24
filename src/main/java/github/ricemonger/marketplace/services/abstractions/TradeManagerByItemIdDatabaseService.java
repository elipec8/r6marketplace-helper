package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeManagerByItemId;

public interface TradeManagerByItemIdDatabaseService {
    void save(TradeManagerByItemId tradeManager);
}
