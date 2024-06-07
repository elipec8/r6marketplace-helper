package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntityId;
import github.ricemonger.marketplace.databases.postgres.mappers.ItemFilterPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemFilterPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemFilterDatabaseService;
import github.ricemonger.utils.dtos.ItemFilter;
import github.ricemonger.utils.exceptions.ItemFilterDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class ItemFilterPostgresService implements ItemFilterDatabaseService {

    private final ItemFilterPostgresRepository repository;

    private final ItemFilterPostgresMapper mapper;

    @Override
    public Collection<ItemFilter> findAllItemFiltersByChatId(String chatId) {
        return mapper.mapItemFilters(repository.findAllByChatId(chatId));
    }

    @Override
    public void saveItemFilter(ItemFilter filter) {
        repository.save(mapper.mapItemFilterEntity(filter));
    }

    @Override
    public ItemFilter findItemFilterById(String chatId, String name) throws ItemFilterDoesntExistException {
        return mapper.mapItemFilter(repository.findById(new ItemFilterEntityId(chatId, name)).orElseThrow(ItemFilterDoesntExistException::new));
    }

    @Override
    public void removeItemFilterById(String chatId, String name) {
        repository.deleteById(new ItemFilterEntityId(chatId, name));
    }

    @Override
    public Collection<String> getAllItemFilterNamesForUser(String chatId) {
        return repository.findAllByChatId(chatId).stream().map(ItemFilterEntity::getName).toList();
    }
}
