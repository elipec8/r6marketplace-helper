package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.item.ItemEntityFactory;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemRepository;

    private final ItemEntityFactory itemEntityFactory;

    @Override
    @Transactional
    public void saveAll(Collection<? extends Item> items) {
        itemRepository.saveAll(itemEntityFactory.createEntities(items));
    }

    @Override
    public Item findById(String itemId) throws ItemDoesntExistException {
        return itemRepository.findById(itemId).map(itemEntityFactory::createDTO).orElseThrow(() -> new ItemDoesntExistException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll().stream().map(itemEntityFactory::createDTO).toList();
    }
}
