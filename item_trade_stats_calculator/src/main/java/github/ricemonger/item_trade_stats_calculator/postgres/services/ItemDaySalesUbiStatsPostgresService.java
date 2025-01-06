package github.ricemonger.item_trade_stats_calculator.postgres.services;


import github.ricemonger.item_trade_stats_calculator.postgres.repositories.ItemDaySalesUbiStatsPostgresRepository;
import github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item.ItemDaySalesUbiStatsEntityMapper;
import github.ricemonger.item_trade_stats_calculator.services.abstractions.ItemSaleUbiStatsDatabaseService;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemDaySalesUbiStatsPostgresService implements ItemSaleUbiStatsDatabaseService {

    private final ItemDaySalesUbiStatsPostgresRepository itemDaySalesUbiStatsRepository;

    private final ItemDaySalesUbiStatsEntityMapper itemDaySalesUbiStatsEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ItemDaySalesUbiStats> findAllForLastMonth() {
        return itemDaySalesUbiStatsRepository.findAllForLastMonth().stream().map(itemDaySalesUbiStatsEntityMapper::createDTO).toList();
    }
}
