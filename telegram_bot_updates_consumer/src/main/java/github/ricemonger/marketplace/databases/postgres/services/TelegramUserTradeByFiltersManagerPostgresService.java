package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.CustomTradeByFiltersManagerPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.TradeByFiltersManagerEntityMapper;
import github.ricemonger.marketplace.services.DTOs.TradeByFiltersManager;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.utils.exceptions.client.TradeByFiltersManagerDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeByFiltersManagerPostgresService implements TelegramUserTradeByFiltersManagerDatabaseService {

    private final CustomTradeByFiltersManagerPostgresRepository tradeByFiltersManagerRepository;

    private final TradeByFiltersManagerEntityMapper tradeByFiltersManagerEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, TradeByFiltersManager tradeManager) {
        tradeByFiltersManagerRepository.save(tradeByFiltersManagerEntityMapper.createEntity(chatId, tradeManager));
    }

    @Override
    @Transactional
    public void invertEnabledFlagById(String chatId, String name) {
        tradeByFiltersManagerRepository.invertEnabledFlagByUserTelegramUserChatIdAndName(chatId, name);
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String name) {
        tradeByFiltersManagerRepository.deleteByUserTelegramUserChatIdAndName(chatId, name);
    }

    @Override
    @Transactional(readOnly = true)
    public TradeByFiltersManager findById(String chatId, String name) {
        return tradeByFiltersManagerEntityMapper.createDTO(tradeByFiltersManagerRepository.findByUserTelegramUserChatIdAndName(chatId, name)
                .orElseThrow(() -> new TradeByFiltersManagerDoesntExistException("Trade by filters manager with name " + name + " doesn't exist for chatId: " + chatId)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TradeByFiltersManager> findAllByChatId(String chatId) {
        return tradeByFiltersManagerRepository.findAllByUserTelegramUserChatId(chatId).stream()
                .map(tradeByFiltersManagerEntityMapper::createDTO)
                .toList();
    }
}
