package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ItemSaleHistoryPostgresMapper {

    public Collection<ItemSaleHistoryEntity> mapItemSaleHistoryEntities(Collection<ItemSaleHistory> histories) {
        if (histories == null || histories.isEmpty()) {
            return List.of();
        } else {
            return histories.stream().map(this::mapItemSaleHistoryEntity).toList();
        }
    }

    public ItemSaleHistoryEntity mapItemSaleHistoryEntity(ItemSaleHistory history) {
        ItemSaleHistoryEntity entity = new ItemSaleHistoryEntity();

        entity.setItemId(history.getItemId());

        entity.setMonthAveragePrice(history.getMonthAveragePrice());
        entity.setMonthMedianPrice(history.getMonthMedianPrice());
        entity.setMonthMaxPrice(history.getMonthMaxPrice());
        entity.setMonthMinPrice(history.getMonthMinPrice());
        entity.setMonthSalesPerDay(history.getMonthSalesPerDay());

        entity.setDayAveragePrice(history.getDayAveragePrice());
        entity.setDayMaxPrice(history.getDayMaxPrice());
        entity.setDayMinPrice(history.getDayMinPrice());
        entity.setDayMedianPrice(history.getDayMedianPrice());
        entity.setDaySales(history.getDaySales());

        entity.setExpectedProfitByCurrentPrices(history.getExpectedProfitByCurrentPrices());
        entity.setExpectedProfitPercentByCurrentPrices(history.getExpectedProfitPercentByCurrentPrices());

        entity.setHoursToSellFor120(history.getHoursToSellFor120());
        entity.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(history.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120());
        entity.setLastDateCurrentPriceOrLastSoldWasHigherThan120(history.getLastDateCurrentPriceOrLastSoldWasHigherThan120());

        entity.setPriceToSellIn1Hour(history.getPriceToSellIn1Hour());
        entity.setPriceToSellIn6Hours(history.getPriceToSellIn6Hours());
        entity.setPriceToSellIn24Hours(history.getPriceToSellIn24Hours());
        entity.setPriceToSellIn168Hours(history.getPriceToSellIn168Hours());

        entity.setPriceToBuyIn1Hour(history.getPriceToBuyIn1Hour());
        entity.setPriceToBuyIn6Hours(history.getPriceToBuyIn6Hours());
        entity.setPriceToBuyIn24Hours(history.getPriceToBuyIn24Hours());
        entity.setPriceToBuyIn168Hours(history.getPriceToBuyIn168Hours());

        return entity;
    }

    public Collection<ItemSaleHistory> mapItemSaleHistories(Collection<ItemSaleHistoryEntity> entities) {
        if (entities == null || entities.isEmpty()) {
            return List.of();
        } else {
            return entities.stream().map(this::mapItemSaleHistory).toList();
        }
    }

    public ItemSaleHistory mapItemSaleHistory(ItemSaleHistoryEntity entity) {
        ItemSaleHistory history = new ItemSaleHistory();

        history.setItemId(entity.getItemId());

        history.setMonthAveragePrice(entity.getMonthAveragePrice());
        history.setMonthMedianPrice(entity.getMonthMedianPrice());
        history.setMonthMaxPrice(entity.getMonthMaxPrice());
        history.setMonthMinPrice(entity.getMonthMinPrice());
        history.setMonthSalesPerDay(entity.getMonthSalesPerDay());

        history.setDayAveragePrice(entity.getDayAveragePrice());
        history.setDayMaxPrice(entity.getDayMaxPrice());
        history.setDayMinPrice(entity.getDayMinPrice());
        history.setDayMedianPrice(entity.getDayMedianPrice());
        history.setDaySales(entity.getDaySales());

        history.setExpectedProfitByCurrentPrices(entity.getExpectedProfitByCurrentPrices());
        history.setExpectedProfitPercentByCurrentPrices(entity.getExpectedProfitPercentByCurrentPrices());

        history.setHoursToSellFor120(entity.getHoursToSellFor120());
        history.setLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120(entity.getLastOrdersCountCurrentPriceOrLastSoldWasHigherThan120());
        history.setLastDateCurrentPriceOrLastSoldWasHigherThan120(entity.getLastDateCurrentPriceOrLastSoldWasHigherThan120());

        history.setPriceToSellIn1Hour(entity.getPriceToSellIn1Hour());
        history.setPriceToSellIn6Hours(entity.getPriceToSellIn6Hours());
        history.setPriceToSellIn24Hours(entity.getPriceToSellIn24Hours());
        history.setPriceToSellIn168Hours(entity.getPriceToSellIn168Hours());

        history.setPriceToBuyIn1Hour(entity.getPriceToBuyIn1Hour());
        history.setPriceToBuyIn6Hours(entity.getPriceToBuyIn6Hours());
        history.setPriceToBuyIn24Hours(entity.getPriceToBuyIn24Hours());
        history.setPriceToBuyIn168Hours(entity.getPriceToBuyIn168Hours());

        return history;
    }
}
