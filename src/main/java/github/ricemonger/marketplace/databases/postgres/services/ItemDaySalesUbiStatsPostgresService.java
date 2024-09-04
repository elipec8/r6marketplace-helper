package github.ricemonger.marketplace.databases.postgres.services;

import github.ricemonger.marketplace.databases.postgres.entities.item.ItemDaySalesUbiStatsEntity;
import github.ricemonger.marketplace.databases.postgres.entities.item.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.marketplace.databases.postgres.repositories.ItemPostgresRepository;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.dtos.ItemDaySales;
import github.ricemonger.utils.dtos.ItemSaleUbiStats;
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
public class ItemDaySalesUbiStatsPostgresService implements ItemSaleUbiStatsService {

    private final ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;

    private final ItemPostgresRepository itemRepository;

    @Transactional
    public void saveAll(Collection<ItemSaleUbiStats> statsList) {
        if (statsList == null) {
            return;
        }

        List<ItemDaySalesUbiStatsEntity> entities = new ArrayList<>();

        for (ItemSaleUbiStats stats : statsList) {
            try {
                ItemEntity itemEntity = itemRepository.findById(stats.getItemId()).orElseThrow(EntityNotFoundException::new);
                for (ItemDaySales itemDaySales : stats.getLast30DaysSales()) {
                    ItemDaySalesUbiStatsEntity salesEntity = new ItemDaySalesUbiStatsEntity(itemEntity, itemDaySales);
                    entities.add(salesEntity);
                }
            } catch (EntityNotFoundException e) {
                log.error("Item with id {} not found", stats.getItemId());
            }
        }
        itemDaySalesUbiStatsRepository.saveAll(entities);
    }
}
