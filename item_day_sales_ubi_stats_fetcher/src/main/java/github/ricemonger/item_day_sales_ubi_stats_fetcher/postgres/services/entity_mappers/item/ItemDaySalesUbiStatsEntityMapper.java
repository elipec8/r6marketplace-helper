package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services.entity_mappers.item;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.ItemDaySalesUbiStatsEntity;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.entities.ItemIdEntity;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDaySalesUbiStatsEntityMapper {

    private final ItemPostgresRepository itemPostgresRepository;

    public List<ItemDaySalesUbiStatsEntity> createEntities(Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList) {
        if (groupedItemDaySalesUbiStatsList == null || groupedItemDaySalesUbiStatsList.isEmpty()) {
            log.error("Empty list of grouped item day sales stats, nothing to save");
            return new LinkedList<>();
        }

        List<ItemIdEntity> existingItemsIds = itemPostgresRepository.findAll();

        List<ItemDaySalesUbiStatsEntity> result = new LinkedList<>();

        for (GroupedItemDaySalesUbiStats groupedStats : groupedItemDaySalesUbiStatsList) {

            ItemIdEntity itemIdEntity = existingItemsIds.stream()
                    .filter(item -> item.getItemId().equals(groupedStats.getItemId()))
                    .findFirst()
                    .orElse(null);

            if (itemIdEntity != null) {
                result.addAll(createEntitiesForItem(itemIdEntity, groupedStats));
            } else {
                log.error("Item with id {} not found, day sales parsing for this item skipped", groupedStats.getItemId());
            }
        }

        return result;
    }

    private List<ItemDaySalesUbiStatsEntity> createEntitiesForItem(ItemIdEntity itemIdEntity, GroupedItemDaySalesUbiStats groupedStats) {
        List<ItemDaySalesUbiStatsEntity> result = new LinkedList<>();

        for (ItemDaySalesUbiStats dto : groupedStats.getDaySales()) {
            result.add(new ItemDaySalesUbiStatsEntity(itemIdEntity,
                    dto.getDate(),
                    dto.getLowestPrice(),
                    dto.getAveragePrice(),
                    dto.getHighestPrice(),
                    dto.getItemsCount()));
        }

        return result;
    }
}
