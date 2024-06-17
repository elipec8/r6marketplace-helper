package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemFilterEntityId;
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

    @Override
    public void save(ItemFilter filter) {
        repository.save(new ItemFilterEntity(filter));
    }

    @Override
    public ItemFilter findById(String chatId, String name) throws ItemFilterDoesntExistException {
        return repository.findById(new ItemFilterEntityId(chatId, name)).orElseThrow(ItemFilterDoesntExistException::new).toItemFilter();
    }

    @Override
    public void deleteById(String chatId, String name) {
        repository.deleteById(new ItemFilterEntityId(chatId, name));
    }

    @Override
    public Collection<ItemFilter> findAllByChatId(String chatId) {
        return repository.findAllByChatId(chatId).stream().map(ItemFilterEntity::toItemFilter).toList();
    }
}
