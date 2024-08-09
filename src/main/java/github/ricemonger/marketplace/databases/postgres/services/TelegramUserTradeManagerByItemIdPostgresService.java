package github.ricemonger.marketplace.databases.postgres.services;


import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeManagerByItemIdEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeManagerByItemIdPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeManagerByItemIdDatabaseService;
import github.ricemonger.utils.dtos.TradeManagerByItemId;
import github.ricemonger.utils.exceptions.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.TradeManagerByItemIdDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeManagerByItemIdPostgresService implements TelegramUserTradeManagerByItemIdDatabaseService {

    private final TradeManagerByItemIdPostgresRepository tradeManagerByItemIdRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, TradeManagerByItemId tradeManager) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        tradeManagerByItemIdRepository.save(new TradeManagerByItemIdEntity(telegramUser.getUser(), tradeManager));
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

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public TradeManagerByItemId findById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeManagerByItemIdDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return tradeManagerByItemIdRepository.findById(new TradeManagerByItemIdEntityId(telegramUser.getUser(), itemId)).map(TradeManagerByItemIdEntity::toTradeManagerByItemId).orElseThrow(() -> new TradeManagerByItemIdDoesntExistException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));
    }

    @Override
    public List<TradeManagerByItemId> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity user = telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return tradeManagerByItemIdRepository.findAllByUserId(user.getUser().getId()).stream().map(TradeManagerByItemIdEntity::toTradeManagerByItemId).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
