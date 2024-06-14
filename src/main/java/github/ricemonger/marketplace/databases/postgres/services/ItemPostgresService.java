package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.mappers.ItemPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemPostgresRepository;

    private final ItemPostgresMapper itemMapper;

    public void saveAllItems(Collection<Item> items) {
        itemPostgresRepository.saveAll(new HashSet<>(itemMapper.mapItemEntities(items)));
    }

    public Collection<Item> findAllItems() {
        return itemMapper.mapItems(itemPostgresRepository.findAll());
    }

    public Collection<Item> findAllItemsByIds(Collection<String> ids) {
        return itemMapper.mapItems(itemPostgresRepository.findAllById(ids));
    }
}
