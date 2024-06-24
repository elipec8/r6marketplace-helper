package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemFilterDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeManagerByItemFilterPostgresService implements TradeManagerByItemFilterDatabaseService {
    @Override
    public void save(TradeManagerByItemFilter tradeManager) {

    }
}
