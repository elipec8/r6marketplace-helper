package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.utils.dtos.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class ItemPostgresMapper {
    public Collection<ItemEntity> mapItemEntities(Collection<Item> items) {
        if (items.isEmpty()) {
            return List.of();
        } else {
            return items.stream().map(this::mapItemEntity).toList();
        }
    }

    public ItemEntity mapItemEntity(Item item) {
        StringBuilder tags = new StringBuilder();

        if (item.getTags() != null) {
            for (String tag : item.getTags()) {
                tags.append(tag).append(",");
            }
            try {
                tags.deleteCharAt(tags.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Tags list is empty");
            }
        }

        ItemEntity entity = new ItemEntity();
        entity.setItemId(item.getItemId());
        entity.setAssetUrl(item.getAssetUrl());
        entity.setName(item.getName());
        entity.setTags(tags.toString());
        entity.setType(item.getType());
        entity.setMaxBuyPrice(item.getMaxBuyPrice());
        entity.setBuyOrdersCount(item.getBuyOrdersCount());
        entity.setMinSellPrice(item.getMinSellPrice());
        entity.setSellOrdersCount(item.getSellOrdersCount());
        entity.setLastSoldAt(item.getLastSoldAt());
        entity.setLastSoldPrice(item.getLastSoldPrice());
        entity.setLimitMinPrice(item.getLimitMinPrice());
        entity.setLimitMaxPrice(item.getLimitMaxPrice());
        return entity;
    }

    public Collection<Item> mapItems(Collection<ItemEntity> entities) {
        if (entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapItem).toList();
        }
    }

    public Item mapItem(ItemEntity entity) {
        List<String> tags;
        if (entity.getTags() != null) {
            tags = List.of(entity.getTags().split(","));
        } else {
            tags = List.of();
        }

        Item item = new Item();
        item.setItemId(entity.getItemId());
        item.setAssetUrl(entity.getAssetUrl());
        item.setName(entity.getName());
        item.setTags(tags);
        item.setType(entity.getType());
        item.setMaxBuyPrice(entity.getMaxBuyPrice());
        item.setBuyOrdersCount(entity.getBuyOrdersCount());
        item.setMinSellPrice(entity.getMinSellPrice());
        item.setSellOrdersCount(entity.getSellOrdersCount());
        item.setLastSoldAt(entity.getLastSoldAt());
        item.setLastSoldPrice(entity.getLastSoldPrice());
        item.setLimitMinPrice(entity.getLimitMinPrice());
        item.setLimitMaxPrice(entity.getLimitMaxPrice());
        return item;
    }
}
