package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemFilterDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemFilters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeManagerByItemFilterPostgresService implements TelegramUserTradeManagerByItemFilterDatabaseService {
    @Override
    //@Transactional
    public void save(TradeManagerByItemFilters tradeManager) {

    }

    @Override
    public List<TradeManagerByItemFilters> findAll(String chatId) {
        return null;
    }
}
