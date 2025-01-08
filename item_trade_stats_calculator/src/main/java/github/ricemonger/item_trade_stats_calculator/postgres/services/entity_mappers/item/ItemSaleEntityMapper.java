package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.dto_projections.ItemSaleDtoProjectionI;
import github.ricemonger.utils.DTOs.common.ItemSale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemSaleEntityMapper {

    public ItemSale createDTO(ItemSaleDtoProjectionI projection) {
        return new ItemSale(projection.getItemId(), projection.getSoldAt(), projection.getPrice());
    }
}
