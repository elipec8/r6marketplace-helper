package github.ricemonger.marketplace.databases.postgres.mappers;

import github.ricemonger.marketplace.databases.postgres.entities.ItemEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleEntity;
import github.ricemonger.marketplace.databases.postgres.entities.ItemSaleHistoryEntity;
import github.ricemonger.utils.dtos.Item;
import github.ricemonger.utils.dtos.ItemSale;
import github.ricemonger.utils.dtos.ItemSaleHistory;
import github.ricemonger.utils.dtos.SoldItemDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class ItemPostgresMapper {
    public Collection<ItemEntity> mapItemEntities(Collection<Item> items) {
        return items.stream().map(this::mapItemEntity).toList();
    }

    public ItemEntity mapItemEntity(Item item) {
        StringBuilder tags = new StringBuilder();

        if(item.getTags() != null) {
            for (String tag : item.getTags()) {
                tags.append(tag).append(",");
            }
            try {
                tags.deleteCharAt(tags.length() - 1);
            } catch (StringIndexOutOfBoundsException e) {
                log.error("Tags list is empty");
            }
        }

        ItemEntity entity = new ItemEntity();
        entity.setItemId(item.getItemId());
        entity.setAssetUrl(item.getAssetUrl());
        entity.setName(item.getName());
        entity.setTags(tags.toString());
        entity.setType(item.getType());
        entity.setMaxBuyPrice(item.getMaxBuyPrice());
        entity.setBuyOrdersCount(item.getBuyOrdersCount());
        entity.setMinSellPrice(item.getMinSellPrice());
        entity.setSellOrdersCount(item.getSellOrdersCount());
        entity.setLastSoldAt(item.getLastSoldAt());
        entity.setLastSoldPrice(item.getLastSoldPrice());
        entity.setLimitMinPrice(item.getLimitMinPrice());
        entity.setLimitMaxPrice(item.getLimitMaxPrice());
        return entity;
    }

    public Collection<Item> mapItems(Collection<ItemEntity> entities) {
        return entities.stream().map(this::mapItem).toList();
    }

    public Item mapItem(ItemEntity entity) {
        List<String> tags;
        if(entity.getTags() != null){
            tags = List.of(entity.getTags().split(","));
        }
        else{
            tags = List.of();
        }

        Item item = new Item();
        item.setItemId(entity.getItemId());
        item.setAssetUrl(entity.getAssetUrl());
        item.setName(entity.getName());
        item.setTags(tags);
        item.setType(entity.getType());
        item.setMaxBuyPrice(entity.getMaxBuyPrice());
        item.setBuyOrdersCount(entity.getBuyOrdersCount());
        item.setMinSellPrice(entity.getMinSellPrice());
        item.setSellOrdersCount(entity.getSellOrdersCount());
        item.setLastSoldAt(entity.getLastSoldAt());
        item.setLastSoldPrice(entity.getLastSoldPrice());
        item.setLimitMinPrice(entity.getLimitMinPrice());
        item.setLimitMaxPrice(entity.getLimitMaxPrice());
        return item;
    }

    public Collection<ItemSaleEntity> mapItemSaleEntities(Collection<? extends SoldItemDetails> items) {
        return items.stream().map(this::mapItemSaleEntity).toList();
    }

    public ItemSaleEntity mapItemSaleEntity(SoldItemDetails item) {
        ItemSaleEntity entity = new ItemSaleEntity();
        entity.setItemId(item.getItemId());
        entity.setSoldAt(item.getLastSoldAt());
        entity.setPrice(item.getLastSoldPrice());
        return entity;
    }

    public Collection<ItemSale> mapItemSales(Collection<ItemSaleEntity> entities) {
        return entities.stream().map(this::mapItemSale).toList();
    }

    public ItemSale mapItemSale(ItemSaleEntity entity) {
        ItemSale item = new ItemSale();
        item.setItemId(entity.getItemId());
        item.setSoldAt(entity.getSoldAt());
        item.setPrice(entity.getPrice());
        return item;
    }

    public Collection<ItemSaleHistoryEntity> mapItemSaleHistoryEntities(Collection<ItemSaleHistory> histories) {
        return histories.stream().map(this::mapItemSaleHistoryEntity).toList();
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
        return entities.stream().map(this::mapItemSaleHistory).toList();
    }

    public ItemSaleHistory mapItemSaleHistory(ItemSaleHistoryEntity entity){
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
