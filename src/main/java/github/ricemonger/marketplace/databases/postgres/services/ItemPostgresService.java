package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemPostgresRepository;

    public void saveAll(Collection<Item> items) {
        if (items != null && !items.isEmpty()) {
            itemPostgresRepository.saveAll(items.stream().map(ItemEntity::new).collect(Collectors.toSet()));
        }
    }

    public Collection<Item> findAll() {
        return itemPostgresRepository.findAll().stream().map(ItemEntity::toItem).toList();
    }
}
