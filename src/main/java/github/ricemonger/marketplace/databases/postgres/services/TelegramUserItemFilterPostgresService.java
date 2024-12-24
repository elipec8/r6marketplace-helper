package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.user.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.entities.user.TelegramUserEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TelegramUserPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserItemFilterPostgresService implements TelegramUserItemFilterDatabaseService {

    private final ItemFilterPostgresRepository itemFilterRepository;

    private final TelegramUserPostgresRepository telegramUserRepository;

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        itemFilterRepository.save(itemFilterEntityMapper.createEntityForTelegramUserChatId(chatId, filter));
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String name) throws TelegramUserDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        List<ItemFilterEntity> filters = telegramUser.getUser().getItemFilters();

        for (int i = 0; i < filters.size(); i++) {
            if (filters.get(i).getName().equals(name)) {
                filters.remove(i);
                break;
            }
        }

        telegramUserRepository.save(telegramUser);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemFilter findById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        TelegramUserEntity telegramUser = getTelegramUserEntityByIdOrThrow(chatId);

        return itemFilterEntityMapper.createDTO(itemFilterRepository.findById(new ItemFilterEntityId(telegramUser.getUser(), name)).orElseThrow(ItemFilterDoesntExistException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemFilter> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        return itemFilterRepository.findAllByUserTelegramUserChatId(chatId).stream().map(itemFilterEntityMapper::createDTO).toList();
    }

    private TelegramUserEntity getTelegramUserEntityByIdOrThrow(String chatId) throws TelegramUserDoesntExistException {
        return telegramUserRepository.findById(chatId).orElseThrow(() -> new TelegramUserDoesntExistException("Telegram user with chatId " + chatId + " not found"));
    }
}
