package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.items.GroupedItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
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
public class ItemDaySalesUbiStatsPostgresService implements ItemSaleUbiStatsService {

    private final ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;

    private final ItemPostgresRepository itemRepository;

    @Override
    @Transactional
    public void saveAll(Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList) {
        if (groupedItemDaySalesUbiStatsList == null || groupedItemDaySalesUbiStatsList.isEmpty()) {
            log.error("Empty list of grouped item day sales stats, nothing to save");
            return;
        }

        Set<String> existingItems = itemRepository.findAllItemIds();

        List<ItemDaySalesUbiStatsEntity> itemDaySalesUbiStatsEntities = new LinkedList<>();

        for (GroupedItemDaySalesUbiStats groupedStats : groupedItemDaySalesUbiStatsList) {
            if (existingItems.contains(groupedStats.getItemId())) {
                ItemEntity itemEntity = itemRepository.getReferenceById(groupedStats.getItemId());
                for (ItemDaySalesUbiStats itemDaySalesUbiStats : groupedStats.getDaySales()) {
                    ItemDaySalesUbiStatsEntity salesEntity = new ItemDaySalesUbiStatsEntity(itemEntity, itemDaySalesUbiStats);
                    itemDaySalesUbiStatsEntities.add(salesEntity);
                }
            } else {
                log.error("Item with id {} not found, day sales parsing for this item skipped", groupedStats.getItemId());
            }
        }



        itemDaySalesUbiStatsRepository.saveAll(itemDaySalesUbiStatsEntities);
    }

    @Override
    public List<ItemDaySalesUbiStats> findAll() {
        return itemDaySalesUbiStatsRepository.findAll().stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList();
    }

    @Override
    public List<ItemDaySalesUbiStats> findAllForLastMonth() {
        return itemDaySalesUbiStatsRepository.findAllForLastMonth().stream().map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats).toList();
    }
}
