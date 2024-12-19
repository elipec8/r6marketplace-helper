package github.ricemonger.marketplace.databases.postgres.services.entity_mappers.item;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
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
public class ItemDaySalesUbiStatsEntityMapper {

    private final ItemPostgresRepository itemPostgresRepository;

    public ItemDaySalesUbiStats createDTO(ItemDaySalesUbiStatsEntity itemDaySalesUbiStatsEntity) {
        return new ItemDaySalesUbiStats(itemDaySalesUbiStatsEntity.getItemId(),
                itemDaySalesUbiStatsEntity.getDate(),
                itemDaySalesUbiStatsEntity.getLowestPrice(),
                itemDaySalesUbiStatsEntity.getAveragePrice(),
                itemDaySalesUbiStatsEntity.getHighestPrice(),
                itemDaySalesUbiStatsEntity.getItemsCount());
    }

    public List<ItemDaySalesUbiStatsEntity> createEntities(Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList) {
        if (groupedItemDaySalesUbiStatsList == null || groupedItemDaySalesUbiStatsList.isEmpty()) {
            log.error("Empty list of grouped item day sales stats, nothing to save");
            return new LinkedList<>();
        }

        Set<String> existingItemsIds = itemPostgresRepository.findAllItemIds();

        List<ItemDaySalesUbiStatsEntity> result = new LinkedList<>();

        for (GroupedItemDaySalesUbiStats groupedStats : groupedItemDaySalesUbiStatsList) {
            if (existingItemsIds.contains(groupedStats.getItemId())) {
                result.addAll(createEntitiesForItem(groupedStats));
            } else {
                log.error("Item with id {} not found, day sales parsing for this item skipped", groupedStats.getItemId());
            }
        }

        return result;
    }

    private List<ItemDaySalesUbiStatsEntity> createEntitiesForItem(GroupedItemDaySalesUbiStats groupedStats) {
        ItemEntity itemReference = itemPostgresRepository.getReferenceById(groupedStats.getItemId());

        List<ItemDaySalesUbiStatsEntity> result = new LinkedList<>();

        for (ItemDaySalesUbiStats dto : groupedStats.getDaySales()) {
            result.add(new ItemDaySalesUbiStatsEntity(itemReference,
                    dto.getDate(),
                    dto.getLowestPrice(),
                    dto.getAveragePrice(),
                    dto.getHighestPrice(),
                    dto.getItemsCount()));
        }

        return result;
    }
}
