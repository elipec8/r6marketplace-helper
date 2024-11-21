package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.DTOs.items.ItemMainFieldsI;
import github.ricemonger.utils.DTOs.items.ItemSale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemPostgresRepository itemRepository;

    @Override
    @Transactional
    public void saveAll(Collection<? extends ItemMainFieldsI> itemsMainFieldsList) {
        if (itemsMainFieldsList == null || itemsMainFieldsList.isEmpty()) {
            return;
        }

        Set<String> existingItems = itemRepository.findAllItemIds();

        List<ItemSaleEntity> salesEntities = new LinkedList<>();

        for (ItemMainFieldsI item : itemsMainFieldsList) {
            if (existingItems.contains(item.getItemId())) {
                ItemEntity itemEntity = itemRepository.getReferenceById(item.getItemId());
                salesEntities.add(new ItemSaleEntity(itemEntity, item.getLastSoldAt(), item.getLastSoldPrice()));
            } else {
                log.error("Item with id {} not found, last sale parsing for this item skipped", item.getItemId());
            }
        }
        itemSaleRepository.saveAll(salesEntities);
    }

    @Override
    public List<ItemSale> findAll() {
        return itemSaleRepository.findAll().stream().map(ItemSaleEntity::toItemSale).toList();
    }

    @Override
    public List<ItemSale> findAllForLastMonth() {
        return itemSaleRepository.findAllForLastMonth().stream().map(ItemSaleEntity::toItemSale).toList();
    }
}

