package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemHistoryFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.item.ItemRecalculationRequiredFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemEntitiesMapper {

    public ItemRecalculationRequiredFields createRecalculationRequiredFieldsDTO(@NotNull ItemRecalculationRequiredFieldsEntity itemEntity) {
        return new ItemRecalculationRequiredFields(
                itemEntity.getItemId(),
                itemEntity.getRarity(),
                itemEntity.getMaxBuyPrice(),
                itemEntity.getBuyOrdersCount(),
                itemEntity.getMinSellPrice(),
                itemEntity.getSellOrdersCount());
    }

    public ItemHistoryFieldsEntity createHistoryFieldsEntity(ItemHistoryFieldsI item) {
        return new ItemHistoryFieldsEntity(
                item.getItemId(),
                item.getMonthAveragePrice(),
                item.getMonthMedianPrice(),
                item.getMonthMaxPrice(),
                item.getMonthMinPrice(),
                item.getMonthSalesPerDay(),
                item.getMonthSales(),
                item.getDayAveragePrice(),
                item.getDayMedianPrice(),
                item.getDayMaxPrice(),
                item.getDayMinPrice(),
                item.getDaySales(),
                item.getPriorityToSellByMaxBuyPrice(),
                item.getPriorityToSellByNextFancySellPrice(),
                item.getPriorityToBuyByMinSellPrice(),
                item.getPriorityToBuyIn1Hour(),
                item.getPriorityToBuyIn6Hours(),
                item.getPriorityToBuyIn24Hours(),
                item.getPriorityToBuyIn168Hours(),
                item.getPriorityToBuyIn720Hours(),
                item.getPriceToBuyIn1Hour(),
                item.getPriceToBuyIn6Hours(),
                item.getPriceToBuyIn24Hours(),
                item.getPriceToBuyIn168Hours(),
                item.getPriceToBuyIn720Hours());
    }
}
