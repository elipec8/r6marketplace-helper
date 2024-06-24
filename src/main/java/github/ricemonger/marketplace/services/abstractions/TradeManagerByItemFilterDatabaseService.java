package github.ricemonger.marketplace.services.abstractions;

import github.ricemonger.utils.dtos.TradeManagerByItemFilter;

public interface TradeManagerByItemFilterDatabaseService {
    void save(TradeManagerByItemFilter tradeManager);
}
