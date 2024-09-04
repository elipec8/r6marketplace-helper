package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemSalePostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemSalePostgresService implements ItemSaleDatabaseService {

    private final ItemSalePostgresRepository itemSaleRepository;

    private final ItemPostgresRepository itemRepository;

    @Transactional
    public void saveAll(Collection<Item> items) {
        if (items == null) {
            return;
        }

        List<ItemSaleEntity> entities = new ArrayList<>();

        for (Item item : items) {
            try {
                ItemEntity itemEntity = itemRepository.findById(item.getItemId()).orElseThrow(EntityNotFoundException::new);
                entities.add(new ItemSaleEntity(itemEntity, item.getLastSoldAt(), item.getLastSoldPrice()));
            } catch (EntityNotFoundException e) {
                log.error("Item with id {} not found", item.getItemId());
            }
        }

        itemSaleRepository.saveAll(entities);
    }

    public List<ItemSale> findAll() {
        return itemSaleRepository.findAll().stream().map(ItemSaleEntity::toItemSale).toList();
    }
}

