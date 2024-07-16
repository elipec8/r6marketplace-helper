package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemFilterDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TradeManagerByItemFilterPostgresService implements TradeManagerByItemFilterDatabaseService {
    @Override
    @Transactional
    public void save(TradeManagerByItemFilters tradeManager) {

    }

    @Override
    public Collection<TradeManagerByItemFilters> findAll(String chatId) {
        return null;
    }
}
