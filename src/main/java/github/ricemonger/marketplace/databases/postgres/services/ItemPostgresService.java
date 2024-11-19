package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.items.Item;
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

    private final TagPostgresRepository tagRepository;

    @Override
    @Transactional
    public void saveAll(Collection<? extends Item> items) {
        Set<TagEntity> tags = new HashSet<>(tagRepository.findAll());
        if (items != null && !items.isEmpty()) {
            itemRepository.saveAll(items.stream().map(item -> new ItemEntity(item, tags)).toList());
        }
    }

    @Override
    public Item findById(String itemId) throws ItemDoesntExistException {
        return itemRepository.findById(itemId).map(ItemEntity::toItem).orElseThrow(() -> new ItemDoesntExistException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll().stream().map(ItemEntity::toItem).toList();
    }
}
