package github.ricemonger.ubi_users_stats_fetcher.postgres.services;

import github.ricemonger.ubi_users_stats_fetcher.postgres.repositories.CustomItemPostgresRepository;
import github.ricemonger.ubi_users_stats_fetcher.postgres.services.entity_mappers.common.ItemEntityMapper;
import github.ricemonger.ubi_users_stats_fetcher.services.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final CustomItemPostgresRepository customItemPostgresRepository;

    private final ItemEntityMapper itemEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<Item> findItemsByIds(List<String> itemIds) {
        return customItemPostgresRepository.findAllByItemIdIn(itemIds).stream().map(itemEntityMapper::createItem).toList();
    }
}
