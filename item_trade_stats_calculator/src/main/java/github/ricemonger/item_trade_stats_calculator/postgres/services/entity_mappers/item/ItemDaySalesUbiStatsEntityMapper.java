package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemDaySalesUbiStatsEntity;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDaySalesUbiStatsEntityMapper {

    public ItemDaySalesUbiStats createDTO(ItemDaySalesUbiStatsEntity itemDaySalesUbiStatsEntity) {
        return new ItemDaySalesUbiStats(itemDaySalesUbiStatsEntity.getItemId_(),
                itemDaySalesUbiStatsEntity.getDate(),
                itemDaySalesUbiStatsEntity.getLowestPrice(),
                itemDaySalesUbiStatsEntity.getAveragePrice(),
                itemDaySalesUbiStatsEntity.getHighestPrice(),
                itemDaySalesUbiStatsEntity.getItemsCount());
    }
}
