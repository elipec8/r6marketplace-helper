package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByFiltersManagerEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeByFiltersManagerPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByFiltersManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByFiltersManager;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeByFiltersManagerPostgresService implements TelegramUserTradeByFiltersManagerDatabaseService {

    private final TradeByFiltersManagerPostgresRepository tradeByFiltersManagerRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, TradeByFiltersManager tradeManager) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        tradeByFiltersManagerRepository.save(new TradeByFiltersManagerEntity(telegramUser.getUser(), tradeManager));
    }

    @Override
    public List<TradeByFiltersManager> findAllByChatId(String chatId) {
        return null;
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
