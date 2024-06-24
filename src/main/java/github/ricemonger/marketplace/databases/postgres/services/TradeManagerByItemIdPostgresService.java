package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedTradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TradeManagerByItemIdPostgresService implements TradeManagerByItemIdDatabaseService {

    private final TradeManagerByItemIdPostgresRepository repository;

    @Override
    public void save(TradeManagerByItemId tradeManager) {
        repository.save(new TelegramLinkedTradeManagerByItemIdEntity(tradeManager));
    }
}
