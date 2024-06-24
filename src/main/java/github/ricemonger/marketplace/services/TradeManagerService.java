package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemFilterDatabaseService;
import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilter;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeManagerService {

    private final TradeManagerByItemIdDatabaseService tradeManagerByItemIdDatabaseService;

    private final TradeManagerByItemFilterDatabaseService tradeManagerByItemFilterDatabaseService;

    public void saveTradeManagerByItemId(TradeManagerByItemId tradeManager) {
        tradeManagerByItemIdDatabaseService.save(tradeManager);
    }

    public void saveTradeManagerByItemFilter(TradeManagerByItemFilter tradeManager) {
        tradeManagerByItemFilterDatabaseService.save(tradeManager);
    }
}
