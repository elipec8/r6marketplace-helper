package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_factories.item.ItemEntityFactory;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemRepository;

    private final ItemEntityFactory itemEntityFactory;

    @Override
    @Transactional
    public void saveAll(Collection<? extends ItemEntityDTO> items) {
        itemRepository.saveAll(itemEntityFactory.createEntities(items));
    }

    @Override
    public ItemEntityDTO findById(String itemId) throws ItemDoesntExistException {
        return itemRepository.findById(itemId).map(itemEntityFactory::createDTO).orElseThrow(() -> new ItemDoesntExistException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    public List<ItemEntityDTO> findAll() {
        return itemRepository.findAll().stream().map(itemEntityFactory::createDTO).toList();
    }
}
