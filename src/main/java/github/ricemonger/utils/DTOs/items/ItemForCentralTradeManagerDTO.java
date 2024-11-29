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
public class ItemForCentralTradeManagerDTO {

    private String itemId;

    private ItemRarity rarity;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime LastSoldAt;
    private Integer lastSoldPrice;

    private Integer monthAveragePrice;
    private Integer monthMedianPrice;
    private Integer monthMaxPrice;
    private Integer monthMinPrice;
    private Integer monthSalesPerDay;

    private Integer dayAveragePrice;
    private Integer dayMedianPrice;
    private Integer dayMaxPrice;
    private Integer dayMinPrice;
    private Integer daySales;

    private Integer priceToSellIn1Hour;
    private Integer priceToSellIn6Hours;
    private Integer priceToSellIn24Hours;
    private Integer priceToSellIn168Hours;

    private Integer priceToBuyIn1Hour;
    private Integer priceToBuyIn6Hours;
    private Integer priceToBuyIn24Hours;
    private Integer priceToBuyIn168Hours;

    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;

    private Integer minBuySellProfit;
    private Integer minProfitPercent;

    private TradeOperationType tradeOperationType;

    private Integer priority;

    public ItemForCentralTradeManagerDTO(Item item, TradeByFiltersManager tradeByFilterManager) {
        setItemFields(item);

        this.sellBoundaryPrice = 0;
        this.buyBoundaryPrice = Integer.MAX_VALUE;
        this.minBuySellProfit = tradeByFilterManager.getMinBuySellProfit();
        this.minProfitPercent = tradeByFilterManager.getMinProfitPercent();

        this.tradeOperationType = tradeByFilterManager.getTradeOperationType();
        this.priority = tradeByFilterManager.getPriority();
    }

    public ItemForCentralTradeManagerDTO(Item item, TradeByItemIdManager tradeByItemIdManager) {
        setItemFields(item);

        this.sellBoundaryPrice = tradeByItemIdManager.getSellBoundaryPrice();
        this.buyBoundaryPrice = tradeByItemIdManager.getBuyBoundaryPrice();
        this.minBuySellProfit = Integer.MIN_VALUE;
        this.minProfitPercent = Integer.MIN_VALUE;

        this.tradeOperationType = tradeByItemIdManager.getTradeOperationType();
        this.priority = tradeByItemIdManager.getPriority();
    }

    private void setItemFields(Item item){
        setItemId(item.getItemId());

        setRarity(item.getRarity());

        setMaxBuyPrice(item.getMaxBuyPrice());
        setBuyOrdersCount(item.getBuyOrdersCount());

        setMinSellPrice(item.getMinSellPrice());
        setSellOrdersCount(item.getSellOrdersCount());

        setLastSoldAt(item.getLastSoldAt());
        setLastSoldPrice(item.getLastSoldPrice());

        setMonthAveragePrice(item.getMonthAveragePrice());
        setMonthMedianPrice(item.getMonthMedianPrice());
        setMonthMaxPrice(item.getMonthMaxPrice());
        setMonthMinPrice(item.getMonthMinPrice());
        setMonthSalesPerDay(item.getMonthSalesPerDay());

        setDayAveragePrice(item.getDayAveragePrice());
        setDayMedianPrice(item.getDayMedianPrice());
        setDayMaxPrice(item.getDayMaxPrice());
        setDayMinPrice(item.getDayMinPrice());
        setDaySales(item.getDaySales());

        setPriceToSellIn1Hour(item.getPriceToSellIn1Hour());
        setPriceToSellIn6Hours(item.getPriceToSellIn6Hours());
        setPriceToSellIn24Hours(item.getPriceToSellIn24Hours());
        setPriceToSellIn168Hours(item.getPriceToSellIn168Hours());

        setPriceToBuyIn1Hour(item.getPriceToBuyIn1Hour());
        setPriceToBuyIn6Hours(item.getPriceToBuyIn6Hours());
        setPriceToBuyIn24Hours(item.getPriceToBuyIn24Hours());
        setPriceToBuyIn168Hours(item.getPriceToBuyIn168Hours());
    }

    public int hashCode() {
        return Objects.hash(itemId, tradeOperationType);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemForCentralTradeManagerDTO itemForTradeDTO)) {
            return false;
        }
        return Objects.equals(itemId, itemForTradeDTO.itemId) && Objects.equals(tradeOperationType, itemForTradeDTO.tradeOperationType);
    }
}
