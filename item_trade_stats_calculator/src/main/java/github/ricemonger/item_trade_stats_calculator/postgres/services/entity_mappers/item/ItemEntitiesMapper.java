package github.ricemonger.item_trade_stats_calculator.postgres.services.entity_mappers.item;

import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemHistoryFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.ItemRecalculationRequiredFieldsEntity;
import github.ricemonger.item_trade_stats_calculator.postgres.entities.TagEntity;
import github.ricemonger.item_trade_stats_calculator.services.DTOs.ItemRecalculationRequiredFields;
import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.ItemHistoryFieldsI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public Item createItem(@NotNull ItemEntity itemEntity) {
        List<String> tags = new ArrayList<>();
        if (itemEntity.getTags() != null && !itemEntity.getTags().isEmpty()) {
            tags = itemEntity.getTags().stream().map(TagEntity::getValue).toList();
        }
        return new Item(
                itemEntity.getItemId(),
                itemEntity.getAssetUrl(),
                itemEntity.getName(),
                tags,
                itemEntity.getRarity(),
                itemEntity.getType(),
                itemEntity.getMaxBuyPrice(),
                itemEntity.getBuyOrdersCount(),
                itemEntity.getMinSellPrice(),
                itemEntity.getSellOrdersCount(),
                itemEntity.getLastSoldAt(),
                itemEntity.getLastSoldPrice(),
                itemEntity.getMonthAveragePrice(),
                itemEntity.getMonthMedianPrice(),
                itemEntity.getMonthMaxPrice(),
                itemEntity.getMonthMinPrice(),
                itemEntity.getMonthSalesPerDay(),
                itemEntity.getMonthSales(),
                itemEntity.getDayAveragePrice(),
                itemEntity.getDayMedianPrice(),
                itemEntity.getDayMaxPrice(),
                itemEntity.getDayMinPrice(),
                itemEntity.getDaySales(),
                itemEntity.getPriorityToSellByMaxBuyPrice(),
                itemEntity.getPriorityToSellByNextFancySellPrice(),
                itemEntity.getPriorityToBuyByMinSellPrice(),
                itemEntity.getPriorityToBuyIn1Hour(),
                itemEntity.getPriorityToBuyIn6Hours(),
                itemEntity.getPriorityToBuyIn24Hours(),
                itemEntity.getPriorityToBuyIn168Hours(),
                itemEntity.getPriorityToBuyIn720Hours(),
                itemEntity.getPriceToBuyIn1Hour(),
                itemEntity.getPriceToBuyIn6Hours(),
                itemEntity.getPriceToBuyIn24Hours(),
                itemEntity.getPriceToBuyIn168Hours(),
                itemEntity.getPriceToBuyIn720Hours());
    }
}
