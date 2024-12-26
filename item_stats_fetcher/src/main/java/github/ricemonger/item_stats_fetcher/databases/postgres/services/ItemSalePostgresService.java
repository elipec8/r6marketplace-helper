package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers.ItemSalePostgresMapper;
import github.ricemonger.item_stats_fetcher.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
@Slf4j
@Component
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemSalePostgresMapper itemSalePostgresMapper;

    @Override
    @Transactional
    public void saveAllItemsLastSales(Collection<? extends SoldItemDetails> items) {
        itemSaleRepository.saveAll(itemSalePostgresMapper.mapToSaleEntities(items));
    }
}
