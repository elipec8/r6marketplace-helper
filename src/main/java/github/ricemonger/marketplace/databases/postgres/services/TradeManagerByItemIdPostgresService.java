package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedTradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.TelegramLinkedTradeManagerByItemIdEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TradeManagerByItemIdPostgresService implements TradeManagerByItemIdDatabaseService {

    private final TradeManagerByItemIdPostgresRepository repository;

    @Override
    public void save(TradeManagerByItemId tradeManager) {
        repository.save(new TelegramLinkedTradeManagerByItemIdEntity(tradeManager));
    }

    @Override
    public void deleteById(String chatId, String itemId) {
        repository.deleteById(new TelegramLinkedTradeManagerByItemIdEntityId(chatId, itemId));
    }

    @Override
    public TradeManagerByItemId findById(String chatId, String itemId) {
        return repository.findById(new TelegramLinkedTradeManagerByItemIdEntityId(chatId, itemId)).map(TelegramLinkedTradeManagerByItemIdEntity::toTradeManagerByItemId).orElseThrow(() -> new TradeManagerByItemIdNotFoundException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));
    }

    @Override
    public Collection<TradeManagerByItemId> findAll(String chatId) {
        return repository.findAllByChatId(chatId).stream().map(TelegramLinkedTradeManagerByItemIdEntity::toTradeManagerByItemId).toList();
    }
}
