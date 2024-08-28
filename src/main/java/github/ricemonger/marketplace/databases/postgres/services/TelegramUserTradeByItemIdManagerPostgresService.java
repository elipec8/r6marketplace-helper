package github.ricemonger.marketplace.databases.postgres.services;


import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.TradeByItemIdManagerEntityId;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TradeByItemIdManagerPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserTradeByItemIdManagerDatabaseService;
import github.ricemonger.utils.dtos.TradeByItemIdManager;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utils.exceptions.client.TradeByItemIdManagerDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserTradeByItemIdManagerPostgresService implements TelegramUserTradeByItemIdManagerDatabaseService {

    private final TradeByItemIdManagerPostgresRepository tradeByItemIdManagerRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, TradeByItemIdManager tradeManager) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        tradeByItemIdManagerRepository.save(new TradeByItemIdManagerEntity(telegramUser.getUser(), tradeManager));
    }

    @Override
    @Transactional
    public void invertEnabledFlagById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        TradeByItemIdManagerEntity manager = tradeByItemIdManagerRepository.findById
                        (new TradeByItemIdManagerEntityId(telegramUser.getUser(), itemId))
                .orElseThrow(() -> new TradeByItemIdManagerDoesntExistException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));

        manager.setEnabled(!manager.isEnabled());

        tradeByItemIdManagerRepository.save(manager);
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String itemId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        List<TradeByItemIdManagerEntity> managers = telegramUser.getUser().getTradeByItemIdManagers();

        Iterator<TradeByItemIdManagerEntity> iterator = managers.iterator();

        while (iterator.hasNext()) {
            TradeByItemIdManagerEntity manager = iterator.next();
            if (manager.getItemId().equals(itemId)) {
                iterator.remove();
                break;
            }
        }

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public TradeByItemIdManager findById(String chatId, String itemId) throws TelegramUserDoesntExistException, TradeByItemIdManagerDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return tradeByItemIdManagerRepository.findById(new TradeByItemIdManagerEntityId(telegramUser.getUser(), itemId)).map(TradeByItemIdManagerEntity::toTradeByItemIdManager).orElseThrow(() -> new TradeByItemIdManagerDoesntExistException(String.format("Trade manager by chatId %s and itemId %s not found", chatId, itemId)));
    }

    @Override
    public List<TradeByItemIdManager> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity user = telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return tradeByItemIdManagerRepository.findAllByUserId(user.getUser().getId()).stream().map(TradeByItemIdManagerEntity::toTradeByItemIdManager).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
