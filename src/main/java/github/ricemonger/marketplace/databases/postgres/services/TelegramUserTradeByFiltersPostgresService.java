package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeByFiltersPostgresService implements TelegramUserTradeByFiltersManagerDatabaseService {
    @Override
    //@Transactional
    public void save(String chatId, TradeByFiltersManager tradeManager) {

    }

    @Override
    public List<TradeByFiltersManager> findAllByChatId(String chatId) {
        return null;
    }
}
