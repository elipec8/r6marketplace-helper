package github.ricemonger.trades_manager.postgres.services;

import github.ricemonger.trades_manager.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.trades_manager.postgres.services.entity_mappers.item.ItemEntityMapper;
import github.ricemonger.trades_manager.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
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
    public List<Item> findAll() {
        return itemRepository.findAll().stream().map(itemEntityMapper::createDTO).toList();
    }
}
