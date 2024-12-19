package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.TagEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.TagPostgresRepository;
import github.ricemonger.utils.DTOs.items.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemEntityMapper {
    private final TagPostgresRepository tagRepository;

    public Item createDTO(@NotNull ItemEntity itemEntity) {
        List<String> tags = new ArrayList<>();
        if (itemEntity.getTags() != null && !itemEntity.getTags().isEmpty()) {
            tags = List.of(itemEntity.getTags().stream().map(TagEntity::getValue).toArray(String[]::new));
        }
        return new Item(
                itemEntity.getItemId(),
                itemEntity.getAssetUrl(),
                itemEntity.getName(),
                tags,
                itemEntity.getRarity(),
                itemEntity.getType(),
                itemEntity.getMaxBuyPrice(),
                itemEntity.getBuyOrdersCount(),
                itemEntity.getMinSellPrice(),
                itemEntity.getSellOrdersCount(),
                itemEntity.getLastSoldAt(),
                itemEntity.getLastSoldPrice(),
                itemEntity.getMonthAveragePrice(),
                itemEntity.getMonthMedianPrice(),
                itemEntity.getMonthMaxPrice(),
                itemEntity.getMonthMinPrice(),
                itemEntity.getMonthSalesPerDay(),
                itemEntity.getMonthSales(),
                itemEntity.getDayAveragePrice(),
                itemEntity.getDayMedianPrice(),
                itemEntity.getDayMaxPrice(),
                itemEntity.getDayMinPrice(),
                itemEntity.getDaySales(),
                itemEntity.getPriorityToSellByMaxBuyPrice(),
                itemEntity.getPriorityToSellByNextFancySellPrice(),
                itemEntity.getPriorityToBuyByMinSellPrice(),
                itemEntity.getPriorityToBuyIn1Hour(),
                itemEntity.getPriorityToBuyIn6Hours(),
                itemEntity.getPriorityToBuyIn24Hours(),
                itemEntity.getPriorityToBuyIn168Hours(),
                itemEntity.getPriorityToBuyIn720Hours(),
                itemEntity.getPriceToBuyIn1Hour(),
                itemEntity.getPriceToBuyIn6Hours(),
                itemEntity.getPriceToBuyIn24Hours(),
                itemEntity.getPriceToBuyIn168Hours(),
                itemEntity.getPriceToBuyIn720Hours());
    }

    public List<ItemEntity> createEntities(Collection<? extends Item> items) {
        List<ItemEntity> entities = new LinkedList<>();

        List<TagEntity> existingTags = tagRepository.findAll();

        for (Item item : items) {
            entities.add(createEntity(item, existingTags));
        }

        return entities;
    }

    private ItemEntity createEntity(Item item, Collection<TagEntity> tageEntities) {
        List<TagEntity> itemTagsEntities = new LinkedList<>();

        if (item.getTags() != null && tageEntities != null && !tageEntities.isEmpty()) {
            for (TagEntity tag : tageEntities) {
                if (item.getTags().contains(tag.getValue())) {
                    itemTagsEntities.add(tag);
                }
            }
        }

        return new ItemEntity(
                item.getItemId(),
                item.getAssetUrl(),
                item.getName(),
                itemTagsEntities,
                item.getRarity(),
                item.getType(),
                item.getMaxBuyPrice(),
                item.getBuyOrdersCount(),
                item.getMinSellPrice(),
                item.getSellOrdersCount(),
                item.getLastSoldAt(),
                item.getLastSoldPrice(),
                item.getMonthAveragePrice(),
                item.getMonthMedianPrice(),
                item.getMonthMaxPrice(),
                item.getMonthMinPrice(),
                item.getMonthSalesPerDay(),
                item.getMonthSales(),
                item.getDayAveragePrice(),
                item.getDayMedianPrice(),
                item.getDayMaxPrice(),
                item.getDayMinPrice(),
                item.getDaySales(),
                item.getPriorityToSellByMaxBuyPrice(),
                item.getPriorityToSellByNextFancySellPrice(),
                item.getPriorityToBuyByMinSellPrice(),
                item.getPriorityToBuyIn1Hour(),
                item.getPriorityToBuyIn6Hours(),
                item.getPriorityToBuyIn24Hours(),
                item.getPriorityToBuyIn168Hours(),
                item.getPriorityToBuyIn720Hours(),
                item.getPriceToBuyIn1Hour(),
                item.getPriceToBuyIn6Hours(),
                item.getPriceToBuyIn24Hours(),
                item.getPriceToBuyIn168Hours(),
                item.getPriceToBuyIn720Hours());
    }
}
