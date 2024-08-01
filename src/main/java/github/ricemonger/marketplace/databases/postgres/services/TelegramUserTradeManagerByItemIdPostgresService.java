package github.ricemonger.marketplace.databases.postgres.services;


import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeManagerByItemIdPostgresService implements TelegramUserTradeManagerByItemIdDatabaseService {

    private final TradeManagerByItemIdPostgresRepository tradeManagerByItemIdPostgresRepository;

    private final TelegramUserPostgresRepository telegramUserPostgresRepository;

    @Override
    @Transactional
    public void save(String chatId, TradeManagerByItemId tradeManager) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        tradeManagerByItemIdPostgresRepository.save(new TradeManagerByItemIdEntity(telegramUser.getUser(), tradeManager));
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String itemId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        List<TradeManagerByItemIdEntity> managers = telegramUser.getUser().getTradeManagersByItemId();

        Iterator<TradeManagerByItemIdEntity> iterator = managers.iterator();

        while (iterator.hasNext()) {
            TradeManagerByItemIdEntity manager = iterator.next();
            if (manager.getItemId().equals(itemId)) {
                iterator.remove();
                break;
            }
        }

        telegramUserPostgresRepository.save(telegramUser);
    }

    @Override
    public TradeManagerByItemId findById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeManagerByItemIdNotFoundException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return tradeManagerByItemIdPostgresRepository.findById(new TradeManagerByItemIdEntityId(telegramUser.getUser(), itemId)).map(TradeManagerByItemIdEntity::toTradeManagerByItemId).orElseThrow(() -> new TradeManagerByItemIdNotFoundException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));
    }

    @Override
    public List<TradeManagerByItemId> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity user = telegramUserPostgresRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return tradeManagerByItemIdPostgresRepository.findAllByUserId(user.getUser().getId()).stream().map(TradeManagerByItemIdEntity::toTradeManagerByItemId).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserPostgresRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
