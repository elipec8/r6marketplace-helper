package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ItemForCentralTradeManager {
    private Item item;

    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;

    private Integer minBuySellProfit;
    private Integer minProfitPercent;

    private TradeOperationType tradeOperationType;

    private Integer priority;

    public ItemForCentralTradeManager(Item item, TradeByFiltersManager tradeByFilterManager) {
        this.item = item;

        this.sellBoundaryPrice = 0;
        this.buyBoundaryPrice = Integer.MAX_VALUE;
        this.minBuySellProfit = tradeByFilterManager.getMinBuySellProfit();
        this.minProfitPercent = tradeByFilterManager.getMinProfitPercent();

        this.tradeOperationType = tradeByFilterManager.getTradeOperationType();
        this.priority = tradeByFilterManager.getPriority();
    }

    public ItemForCentralTradeManager(Item item, TradeByItemIdManager tradeByItemIdManager) {
        this.item = item;

        this.sellBoundaryPrice = tradeByItemIdManager.getSellBoundaryPrice();
        this.buyBoundaryPrice = tradeByItemIdManager.getBuyBoundaryPrice();
        this.minBuySellProfit = Integer.MIN_VALUE;
        this.minProfitPercent = Integer.MIN_VALUE;

        this.tradeOperationType = tradeByItemIdManager.getTradeOperationType();
        this.priority = tradeByItemIdManager.getPriority();
    }

    public String getItemId() {
        return item.getItemId();
    }

    public ItemRarity getRarity() {
        return item.getRarity();
    }

    public Integer getMaxBuyPrice() {
        return item.getMaxBuyPrice();
    }

    public Integer getBuyOrdersCount() {
        return item.getBuyOrdersCount();
    }

    public Integer getMinSellPrice() {
        return item.getMinSellPrice();
    }

    public Integer getSellOrdersCount() {
        return item.getSellOrdersCount();
    }

    public LocalDateTime getLastSoldAt() {
        return item.getLastSoldAt();
    }

    public Integer getLastSoldPrice() {
        return item.getLastSoldPrice();
    }

    public Integer getMonthAveragePrice() {
        return item.getMonthAveragePrice();
    }

    public Integer getMonthMedianPrice() {
        return item.getMonthMedianPrice();
    }

    public Integer getMonthMaxPrice() {
        return item.getMonthMaxPrice();
    }

    public Integer getMonthMinPrice() {
        return item.getMonthMinPrice();
    }

    public Integer getMonthSalesPerDay() {
        return item.getMonthSalesPerDay();
    }

    public Integer getDayAveragePrice() {
        return item.getDayAveragePrice();
    }

    public Integer getDayMedianPrice() {
        return item.getDayMedianPrice();
    }

    public Integer getDayMaxPrice() {
        return item.getDayMaxPrice();
    }

    public Integer getDayMinPrice() {
        return item.getDayMinPrice();
    }

    public Integer getDaySales() {
        return item.getDaySales();
    }

    public Integer getPriceToSellIn1Hour() {
        return item.getPriceToSellIn1Hour();
    }

    public Integer getPriceToSellIn6Hours() {
        return item.getPriceToSellIn6Hours();
    }

    public Integer getPriceToSellIn24Hours() {
        return item.getPriceToSellIn24Hours();
    }

    public Integer getPriceToSellIn168Hours() {
        return item.getPriceToSellIn168Hours();
    }

    public Integer getPriceToBuyIn1Hour() {
        return item.getPriceToBuyIn1Hour();
    }

    public Integer getPriceToBuyIn6Hours() {
        return item.getPriceToBuyIn6Hours();
    }

    public Integer getPriceToBuyIn24Hours() {
        return item.getPriceToBuyIn24Hours();
    }

    public Integer getPriceToBuyIn168Hours() {
        return item.getPriceToBuyIn168Hours();
    }

    public int hashCode() {
        return Objects.hash(item, tradeOperationType);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemForCentralTradeManager itemForTradeDTO)) {
            return false;
        }
        return Objects.equals(item, itemForTradeDTO.item) && Objects.equals(tradeOperationType, itemForTradeDTO.tradeOperationType);
    }
}
