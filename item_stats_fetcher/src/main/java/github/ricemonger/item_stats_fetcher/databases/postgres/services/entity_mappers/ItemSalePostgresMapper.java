package github.ricemonger.item_stats_fetcher.databases.postgres.services.entity_mappers;

import github.ricemonger.item_stats_fetcher.databases.postgres.custom_entities.ItemSaleEntity;
import github.ricemonger.item_stats_fetcher.databases.postgres.repositories.CustomItemMainFieldsPostgresRepository;
import github.ricemonger.utils.DTOs.common.SoldItemDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ItemSalePostgresMapper {

    private final CustomItemMainFieldsPostgresRepository itemMainFieldsRepository;

    public List<ItemSaleEntity> mapToSaleEntities(Collection<? extends SoldItemDetails> items) {

        Set<String> existingItems = itemMainFieldsRepository.findAllItemIds();

        return items.stream()
                .filter(item -> existingItems.contains(item.getItemId()))
                .map(this::mapSaleEntity)
                .toList();
    }

    private ItemSaleEntity mapSaleEntity(SoldItemDetails item) {
        return new ItemSaleEntity(
                itemMainFieldsRepository.getReferenceById(item.getItemId()),
                item.getLastSoldAt(),
                item.getLastSoldPrice()
        );
    }
}
