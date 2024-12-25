package github.ricemonger.item_stats_fetcher.databases.postgres.services;

import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemMainFieldsPostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.TagValuePostgresRepository;
import github.ricemonger.item_stats_fetcher.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private ItemMainFieldsPostgresRepository itemMainFieldsRepository;

    private ItemSalePostgresRepository itemSaleRepository;

    private TagValuePostgresRepository tagValueRepository;


    @Override
    public void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> items) {
    }

    @Override
    public void saveAllItemsLastSales(Collection<? extends SoldItemDetails> items) {
    }
}
