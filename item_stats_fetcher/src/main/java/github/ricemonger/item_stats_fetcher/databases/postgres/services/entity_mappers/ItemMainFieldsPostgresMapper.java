package github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers;

import github.ricemonger.item_stats_fetcher.databases.postgres.entities.ItemMainFieldsEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.entities.TagValueEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.TagValuePostgresRepository;
import github.ricemonger.utils.DTOs.common.ItemMainFieldsI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemMainFieldsPostgresMapper {

    private final TagValuePostgresRepository tagValueRepository;

    public List<ItemMainFieldsEntity> mapToEntities(Collection<? extends ItemMainFieldsI> items) {
        List<TagValueEntity> existingTagValues = tagValueRepository.findAll();

        return items.stream()
                .map(item -> mapEntity(item, existingTagValues))
                .toList();
    }

    private ItemMainFieldsEntity mapEntity(ItemMainFieldsI item, Collection<TagValueEntity> existingTagValues) {
        return new ItemMainFieldsEntity(
                item.getItemId(),
                item.getAssetUrl(),
                item.getName(),
                existingTagValues.stream()
                        .filter(tagValue -> item.getTags().contains(tagValue.getValue()))
                        .toList(),
                item.getRarity(),
                item.getType(),
                item.getMaxBuyPrice(),
                item.getBuyOrdersCount(),
                item.getMinSellPrice(),
                item.getSellOrdersCount(),
                item.getLastSoldAt(),
                item.getLastSoldPrice()
        );
    }
}
