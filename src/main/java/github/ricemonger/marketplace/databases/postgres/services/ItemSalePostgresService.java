package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemPostgresRepository itemRepository;

    @Override
    @Transactional
    public void saveAll(Collection<Item> items) {
        if (items == null) {
            return;
        }

        Set<String> existingItems = itemRepository.findAllItemIds();

        List<ItemSaleEntity> salesEntities = new LinkedList<>();

        for (Item item : items) {
            try {
                if (!existingItems.contains(item.getItemId())) {
                    log.error("Item with id {} not found, last sale parsing for this item skipped", item.getItemId());
                    continue;
                }
                ItemEntity itemEntity = itemRepository.getReferenceById(item.getItemId());
                salesEntities.add(new ItemSaleEntity(itemEntity, item.getLastSoldAt(), item.getLastSoldPrice()));
            } catch (EntityNotFoundException e) {
                log.error("Item with id {} not found", item.getItemId());
            }
        }
        itemSaleRepository.saveAll(salesEntities);
    }

    @Override
    public List<ItemSale> findAll() {
        return itemSaleRepository.findAll().stream().map(ItemSaleEntity::toItemSale).toList();
    }
}

