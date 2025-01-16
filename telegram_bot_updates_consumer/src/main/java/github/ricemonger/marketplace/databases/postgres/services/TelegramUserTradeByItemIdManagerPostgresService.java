package github.ricemonger.marketplace.databases.postgres.services;


import github.ricemonger.marketplace.databases.postgres.repositories.CustomTradeByItemIdManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByItemIdManagerEntityMapper;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.DTOs.personal.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TradeByItemIdManagerDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeByItemIdManagerPostgresService implements TelegramUserTradeByItemIdManagerDatabaseService {

    private final CustomTradeByItemIdManagerPostgresRepository tradeByItemIdManagerRepository;

    private final TradeByItemIdManagerEntityMapper tradeByItemIdManagerEntityMapper;

    @Override
    public void save(String chatId, TradeByItemIdManager tradeManager) {
        tradeByItemIdManagerRepository.save(tradeByItemIdManagerEntityMapper.createEntity(chatId, tradeManager));
    }

    @Override
    public void invertEnabledFlagById(String chatId, String itemId) {
        tradeByItemIdManagerRepository.invertEnabledFlagByUserTelegramUserChatIdAndItemItemId(chatId, itemId);
    }

    @Override
    public void deleteById(String chatId, String itemId) {
        tradeByItemIdManagerRepository.deleteByUserTelegramUserChatIdAndItemItemId(chatId, itemId);
    }

    @Override
    public TradeByItemIdManager findById(String chatId, String itemId) {
        return tradeByItemIdManagerRepository.findByUserTelegramUserChatIdAndItemItemId(chatId, itemId)
                .map(tradeByItemIdManagerEntityMapper::createDTO)
                .orElseThrow(() -> new TradeByItemIdManagerDoesntExistException("Trade by itemId manager with itemId " + itemId + " doesn't exist for chatId: " + chatId));
    }

    @Override
    public List<TradeByItemIdManager> findAllByChatId(String chatId) {
        return tradeByItemIdManagerRepository.findAllByUserTelegramUserChatId(chatId).stream().map(tradeByItemIdManagerEntityMapper::createDTO).toList();
    }
}
