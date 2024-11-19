package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.items.ItemDaySalesUbiStats;
import github.ricemonger.utils.DTOs.items.ItemSalesUbiStatsByItemId;
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
    public void saveAllSales(Collection<ItemSalesUbiStatsByItemId> statsList) {
        if (statsList == null) {
            return;
        }

        Set<String> existingItems = itemRepository.findAllItemIds();

        List<ItemDaySalesUbiStatsEntity> daySalesEntities = new LinkedList<>();

        for (ItemSalesUbiStatsByItemId stats : statsList) {
            try {
                if (!existingItems.contains(stats.getItemId())) {
                    log.error("Item with id {} not found, day sales parsing for this item skipped", stats.getItemId());
                    continue;
                }
                ItemEntity itemEntity = itemRepository.getReferenceById(stats.getItemId());
                for (ItemDaySalesUbiStats itemDaySalesUbiStats : stats.getDaySales()) {
                    ItemDaySalesUbiStatsEntity salesEntity = new ItemDaySalesUbiStatsEntity(itemEntity, itemDaySalesUbiStats);
                    daySalesEntities.add(salesEntity);
                }
            } catch (Throwable e) {
                log.error("Item with id {} not found", stats.getItemId());
            }
        }
        itemDaySalesUbiStatsRepository.saveAll(daySalesEntities);
    }

    @Override
    public List<ItemDaySalesUbiStats> findAllDaySales() {
        return itemDaySalesUbiStatsRepository.findAll().stream()
                .map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats)
                .toList();
    }

    @Override
    public List<ItemDaySalesUbiStats> findAllLastMonthSales() {
        return itemDaySalesUbiStatsRepository.findAllLastMonthSales().stream()
                .map(ItemDaySalesUbiStatsEntity::toItemDaySalesUbiStats)
                .toList();

    }
}
