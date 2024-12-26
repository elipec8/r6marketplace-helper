package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemMainFieldsPostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers.ItemMainFieldsPostgresMapper;
import github.ricemonger.item_stats_fetcher.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemMainFieldsPostgresRepository itemMainFieldsRepository;

    private final ItemMainFieldsPostgresMapper itemMainFieldsPostgresMapper;

    @Override
    @Transactional
    public void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> items) {
        itemMainFieldsRepository.saveAll(itemMainFieldsPostgresMapper.mapToEntities(items));
    }
}
