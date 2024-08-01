package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemRepository;

    @Override
    public void saveAll(Collection<Item> items) {
        if (items != null && !items.isEmpty()) {
            itemRepository.saveAll(items.stream().map(ItemEntity::new).collect(Collectors.toSet()));
        }
    }

    @Override
    public Item findById(String itemId) throws ItemNotFoundException {
        return itemRepository.findById(itemId).map(ItemEntity::toItem).orElseThrow(() -> new ItemNotFoundException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    public List<Item> findAll() {
        return itemRepository.findAll().stream().map(ItemEntity::toItem).toList();
    }
}
