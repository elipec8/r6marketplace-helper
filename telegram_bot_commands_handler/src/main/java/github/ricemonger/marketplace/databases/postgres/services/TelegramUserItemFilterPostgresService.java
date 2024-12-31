package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresEntity;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.user.ItemFilterEntityMapper;
import github.ricemonger.marketplace.services.abstractions.TelegramUserItemFilterDatabaseService;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import github.ricemonger.utils.exceptions.client.ItemFilterDoesntExistException;
import github.ricemonger.utils.exceptions.client.TelegramUserDoesntExistException;
import github.ricemonger.utilspostgresschema.full_entities.user.ItemFilterEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelegramUserItemFilterPostgresService implements TelegramUserItemFilterDatabaseService {

    private final ItemFilterPostgresEntity itemFilterRepository;

    private final ItemFilterEntityMapper itemFilterEntityMapper;

    @Override
    @Transactional
    public void save(String chatId, ItemFilter filter) throws TelegramUserDoesntExistException {
        itemFilterRepository.save(itemFilterEntityMapper.createEntity(chatId, filter));
    }

    @Override
    @Transactional
    public void deleteById(String chatId, String name) throws TelegramUserDoesntExistException {
        itemFilterRepository.deleteByUserTelegramUserChatIdAndName(chatId, name);
    }

    @Override
    @Transactional(readOnly = true)
    public ItemFilter findById(String chatId, String name) throws TelegramUserDoesntExistException, ItemFilterDoesntExistException {
        return itemFilterEntityMapper.createDTO(itemFilterRepository.findByUserTelegramUserChatIdAndName(chatId, name).orElseThrow(ItemFilterDoesntExistException::new));
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findAllNamesByChatId(String chatId) throws TelegramUserDoesntExistException {
        return itemFilterRepository.findAllNamesByUserTelegramUserChatId(chatId).stream().map(ItemFilterEntity::getName).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemFilter> findAllByChatId(String chatId) throws TelegramUserDoesntExistException {
        return itemFilterRepository.findAllByUserTelegramUserChatId(chatId).stream().map(itemFilterEntityMapper::createDTO).toList();
    }
}
