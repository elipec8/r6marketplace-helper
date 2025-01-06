package github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services;

import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.postgres.services.entity_mappers.item.ItemDaySalesUbiStatsEntityMapper;
import github.ricemonger.item_day_sales_ubi_stats_fetcher.services.abstractions.ItemSaleUbiStatsDatabaseService;
import github.ricemonger.utils.DTOs.common.GroupedItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemDaySalesUbiStatsPostgresDatabaseService implements ItemSaleUbiStatsDatabaseService {

    private final ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;

    private final ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Override
    @Transactional
    public void saveAll(Collection<GroupedItemDaySalesUbiStats> groupedItemDaySalesUbiStatsList) {
        itemDaySalesUbiStatsRepository.saveAll(itemDaySalesUbiStatsEntityMapper.createEntities(groupedItemDaySalesUbiStatsList));
    }
}
