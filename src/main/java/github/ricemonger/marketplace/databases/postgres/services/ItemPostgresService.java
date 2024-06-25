package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.exceptions.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemPostgresRepository;

    @Override
    public void saveAll(Collection<Item> items) {
        if (items != null && !items.isEmpty()) {
            itemPostgresRepository.saveAll(items.stream().map(ItemEntity::new).collect(Collectors.toSet()));
        }
    }

    @Override
    public Item findById(String itemId) throws ItemNotFoundException{
        return itemPostgresRepository.findById(itemId).map(ItemEntity::toItem).orElseThrow(() -> new ItemNotFoundException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    public Collection<Item> findAll() {
        return itemPostgresRepository.findAll().stream().map(ItemEntity::toItem).toList();
    }
}
