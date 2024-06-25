package github.ricemonger.marketplace.databases.postgres.services;


import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TradeManagerByItemIdPostgresService implements TradeManagerByItemIdDatabaseService {

    private final TradeManagerByItemIdPostgresRepository tradeManagerRepository;

    private final TelegramUserPostgresRepository userRepository;

    @Override
    public void save(String chatId, TradeManagerByItemId tradeManager) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        tradeManagerRepository.save(new TradeManagerByItemIdEntity(user.getUser(), tradeManager));
    }

    @Override
    public void deleteById(String chatId, String itemId) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        tradeManagerRepository.deleteById(new TradeManagerByItemIdEntityId(user.getUser().getId(), itemId));
    }

    @Override
    public TradeManagerByItemId findById(String chatId, String itemId) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return tradeManagerRepository.findById(new TradeManagerByItemIdEntityId(user.getUser().getId(), itemId)).map(TradeManagerByItemIdEntity::toTradeManagerByItemId).orElseThrow(() -> new TradeManagerByItemIdNotFoundException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));
    }

    @Override
    public Collection<TradeManagerByItemId> findAll(String chatId) {
        TelegramUserEntity user = userRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return tradeManagerRepository.findAllByUserId(user.getUser().getId()).stream().map(TradeManagerByItemIdEntity::toTradeManagerByItemId).toList();
    }
}
