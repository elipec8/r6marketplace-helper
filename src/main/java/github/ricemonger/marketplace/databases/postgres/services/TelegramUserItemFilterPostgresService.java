package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserItemFilterPostgresService implements TelegramUserItemFilterDatabaseService {

    private final ItemFilterPostgresRepository itemFilterRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    @Override
    @Transactional
    public void save(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        itemFilterRepository.save(new ItemFilterEntity(telegramUser.getUser(), filter));
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String name) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        List<ItemFilterEntity> filters = telegramUser.getUser().getItemFilters();

        Iterator<ItemFilterEntity> iterator = filters.iterator();

        while (iterator.hasNext()) {
            ItemFilterEntity filter = iterator.next();
            if (filter.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }

        telegramUserRepository.save(telegramUser);
    }

    @Override
    public ItemFilter findById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return itemFilterRepository.findById(new ItemFilterEntityId(telegramUser.getUser(), name)).orElseThrow(ItemFilterDoesntExistException::new).toItemFilter();
    }

    @Override
    public List<ItemFilter> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("User with chatId " + chatId + " doesn't exist"));

        return itemFilterRepository.findAllByUserId(telegramUser.getUser().getId()).stream().map(ItemFilterEntity::toItemFilter).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
