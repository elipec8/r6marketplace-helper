package github.ricemonger.marketplace.databases.postgres.services.entity_factories.item;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.ItemSaleEntityDTO;
import github.ricemonger.utils.DTOs.items.SoldItemDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemSaleEntityFactory {

    private final ItemPostgresRepository itemRepository;

    public ItemSaleEntityDTO createDTO(ItemSaleEntity entity) {
        return new ItemSaleEntityDTO(entity.getItemId(), entity.getSoldAt(), entity.getPrice());
    }

    public Iterable<ItemSaleEntity> createEntities(Collection<? extends SoldItemDetails> soldItems) {
        Set<String> existingItemsIds = itemRepository.findAllItemIds();

        List<ItemSaleEntity> entities = new LinkedList<>();

        for (SoldItemDetails soldItem : soldItems) {
            if (existingItemsIds.contains(soldItem.getItemId())) {
                entities.add(new ItemSaleEntity(itemRepository.getReferenceById(soldItem.getItemId()), soldItem.getLastSoldAt(), soldItem.getLastSoldPrice()));
            } else {
                log.info("Item with id {} doesn't exist", soldItem.getItemId());
            }
        }
        return entities;
    }
}
