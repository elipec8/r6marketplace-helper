package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item.ItemEntityMapper;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.exceptions.client.ItemDoesntExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemRepository;

    private final ItemEntityMapper itemEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Item findById(String itemId) throws ItemDoesntExistException {
        return itemRepository.findById(itemId).map(itemEntityMapper::createDTO).orElseThrow(() -> new ItemDoesntExistException("Item with id" + itemId + "doesn't exist"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Item> findAll() {
        return itemRepository.findAll().stream().map(itemEntityMapper::createDTO).toList();
    }
}
