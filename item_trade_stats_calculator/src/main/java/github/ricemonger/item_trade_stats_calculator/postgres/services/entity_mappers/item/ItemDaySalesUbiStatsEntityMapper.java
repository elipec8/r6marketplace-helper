package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemDaySalesUbiStatsProjectionI;
import github.ricemonger.utils.DTOs.common.ItemDaySalesUbiStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDaySalesUbiStatsEntityMapper {

    public ItemDaySalesUbiStats createDTO(ItemDaySalesUbiStatsProjectionI projection) {
        return new ItemDaySalesUbiStats(projection.getItemId(),
                projection.getDate(),
                projection.getLowestPrice(),
                projection.getAveragePrice(),
                projection.getHighestPrice(),
                projection.getItemsCount());
    }
}
