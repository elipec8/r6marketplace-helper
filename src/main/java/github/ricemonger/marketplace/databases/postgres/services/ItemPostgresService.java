package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.mappers.ItemPostgresMapper;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSaleHistoryPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ItemPostgresService implements ItemDatabaseService {

    private final ItemPostgresRepository itemPostgresRepository;

    private final ItemSalePostgresRepository itemSalePostgresRepository;

    private final ItemSaleHistoryPostgresRepository itemSaleHistoryPostgresRepository;

    private final ItemPostgresMapper mapper;

    public void saveAllItemsAndItemSales(Collection<Item> items) {
        if(items.isEmpty()) {
            return;
        }
        else {
            Set<ItemEntity> entities = new HashSet<>(mapper.mapItemEntities(items));

            Set<ItemSaleEntity> saleEntities = new HashSet<>(mapper.mapItemSaleEntities(items));

            itemPostgresRepository.saveAll(entities);
            itemSalePostgresRepository.saveAll(saleEntities);
        }
    }

    public Collection<Item> findAllItems(){
        Collection<ItemEntity> entities = itemPostgresRepository.findAll();
        if(entities.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return mapper.mapItems(entities);
        }
    }

    public Collection<ItemSale> findAllItemSales(){
        Collection<ItemSaleEntity> entities = itemSalePostgresRepository.findAll();
        if(entities.isEmpty()) {
            return new ArrayList<>();
        }
        else {
            return mapper.mapItemSales(entities);
        }
    }

    public void saveAllItemSaleHistoryStats(Collection<ItemSaleHistory> histories){
        if(histories.isEmpty())
            return;
        else {
            itemSaleHistoryPostgresRepository.saveAll(mapper.mapItemSaleHistoryEntities(histories));
        }
    }

    public Collection<Item> findAllItemsByIds(Collection<String> ids) {
        if(ids.isEmpty())
            return new ArrayList<>();
        else{
            return mapper.mapItems(itemPostgresRepository.findAllById(ids));
        }
    }
}
