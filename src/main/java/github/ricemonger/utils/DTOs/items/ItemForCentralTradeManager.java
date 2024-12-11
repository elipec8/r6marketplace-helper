package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.DTOs.UbiTrade;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeCategory;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

import static github.ricemonger.marketplace.services.CentralTradeManager.*;

@Data
public class ItemForCentralTradeManager {

    private Item item;

    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;

    private Integer minBuySellProfit;
    private Integer minBuySellProfitPercent;

    private TradeOperationType tradeOperationType;

    private Integer priority;

    private Boolean isOwned;

    private Long tradePriority;
    private Integer tradePrice;
    private Integer prognosedTradeSuccessMinutes;

    public ItemForCentralTradeManager(Item item, TradeByFiltersManager tradeByFilterManager, int feePercentage, Collection<String> ownedItems) {
        this.item = item;

        this.sellBoundaryPrice = Integer.MIN_VALUE;
        this.buyBoundaryPrice = Integer.MAX_VALUE;
        this.minBuySellProfit = tradeByFilterManager.getMinBuySellProfit();
        this.minBuySellProfitPercent = tradeByFilterManager.getMinProfitPercent();

        this.tradeOperationType = tradeByFilterManager.getTradeOperationType();
        this.priority = tradeByFilterManager.getPriority();

        this.isOwned = ownedItems.contains(item.getItemId());

        TradePriorityPriceAndSuccessMinutes tradePriorityPriceAndSuccessMinutes = getTradePriorityAndPrice(item, buyBoundaryPrice,
                sellBoundaryPrice, minBuySellProfit, minBuySellProfitPercent, priority, isOwned, tradeOperationType, feePercentage);

        this.tradePriority = tradePriorityPriceAndSuccessMinutes.tradePriority();
        this.tradePrice = tradePriorityPriceAndSuccessMinutes.price();
        this.prognosedTradeSuccessMinutes = tradePriorityPriceAndSuccessMinutes.prognosedTradeSuccessMinutes();
    }

    public ItemForCentralTradeManager(Item item, TradeByItemIdManager tradeByItemIdManager, int feePercentage, Collection<String> ownedItems) {
        this.item = item;

        this.sellBoundaryPrice = tradeByItemIdManager.getSellBoundaryPrice();
        this.buyBoundaryPrice = tradeByItemIdManager.getBuyBoundaryPrice();
        this.minBuySellProfit = Integer.MIN_VALUE;
        this.minBuySellProfitPercent = Integer.MIN_VALUE;

        this.tradeOperationType = tradeByItemIdManager.getTradeOperationType();
        this.priority = tradeByItemIdManager.getPriority();

        this.isOwned = ownedItems.contains(item.getItemId());

        TradePriorityPriceAndSuccessMinutes tradePriorityPriceAndSuccessMinutes = getTradePriorityAndPrice(item, buyBoundaryPrice,
                sellBoundaryPrice, minBuySellProfit, minBuySellProfitPercent, priority, isOwned, tradeOperationType, feePercentage);

        this.tradePriority = tradePriorityPriceAndSuccessMinutes.tradePriority();
        this.tradePrice = tradePriorityPriceAndSuccessMinutes.price();
        this.prognosedTradeSuccessMinutes = tradePriorityPriceAndSuccessMinutes.prognosedTradeSuccessMinutes();
    }

    public static Long getExistingUbiTradePriority(UbiTrade ubiTrade) {
        if (ubiTrade.getCategory().equals(TradeCategory.Buy)) {
            return ItemForCentralTradeManager.getBuyTradePriority(Integer.MAX_VALUE, ubiTrade.getProposedPaymentPrice(),
                    ubiTrade.getPrognosedPaymentsSuccessMinutes(), 1).tradePriority;
        } else {
            return ItemForCentralTradeManager.getSellTradePriority(Integer.MIN_VALUE, ubiTrade.getProposedPaymentPrice(),
                    ubiTrade.getPrognosedPaymentsSuccessMinutes(), 1).tradePriority;
        }
    }

    private static TradePriorityPriceAndSuccessMinutes getTradePriorityAndPrice(Item item, Integer buyBoundaryPrice, Integer sellBoundaryPrice,
                                                                                Integer minBuySellProfit, Integer minBuySellProfitPercent, Integer priority,
                                                                                boolean isOwned, TradeOperationType tradeOperationType, int feePercentage) {
        if (tradeOperationType == TradeOperationType.BUY) {
            return getMaxBuyPriority(item, buyBoundaryPrice, priority);
        } else if (tradeOperationType == TradeOperationType.SELL) {
            return getMaxSellPriority(item, sellBoundaryPrice, priority);
        } else {
            return getBuySellTradePriorityAndPrice(item, buyBoundaryPrice, sellBoundaryPrice, minBuySellProfit, minBuySellProfitPercent, priority, isOwned, feePercentage);
        }
    }

    private static TradePriorityPriceAndSuccessMinutes getBuySellTradePriorityAndPrice(Item item, Integer buyBoundaryPrice, Integer sellBoundaryPrice,
                                                                                       Integer minBuySellProfit, Integer minBuySellProfitPercent, Integer priority,
                                                                                       boolean isOwned, int feePercentage) {
        TradePriorityPriceAndSuccessMinutes buyMaxTradePriorityPriceAndSuccessMinutes = getMaxBuyPriority(item, buyBoundaryPrice, priority);
        TradePriorityPriceAndSuccessMinutes sellMaxTradePriorityPriceAndSuccessMinutes = getMaxSellPriority(item, sellBoundaryPrice, priority);

        if (buyMaxTradePriorityPriceAndSuccessMinutes.tradePriority() == 0 || sellMaxTradePriorityPriceAndSuccessMinutes.tradePriority() == 0) {
            return new TradePriorityPriceAndSuccessMinutes(0, 0, 0);
        } else {
            int buyPrice = buyMaxTradePriorityPriceAndSuccessMinutes.price();
            int sellPrice = sellMaxTradePriorityPriceAndSuccessMinutes.price();
            int profit = sellPrice - buyPrice - (sellPrice * feePercentage / 100);
            int profitPercent = (profit * 100) / (buyPrice == 0 ? 1 : buyPrice);

            if (profit < minBuySellProfit || profitPercent < minBuySellProfitPercent) {
                return new TradePriorityPriceAndSuccessMinutes(0, 0, 0);
            }

            long tradePriority;
            int tradePrice;
            int prognosedTradeSuccessMinutes;

            if (isOwned) {
                tradePrice = sellPrice;
                tradePriority = buyMaxTradePriorityPriceAndSuccessMinutes.tradePriority();
                prognosedTradeSuccessMinutes = buyMaxTradePriorityPriceAndSuccessMinutes.prognosedTradeSuccessMinutes();
            } else {
                tradePrice = buyPrice;
                tradePriority = sellMaxTradePriorityPriceAndSuccessMinutes.tradePriority();
                prognosedTradeSuccessMinutes = sellMaxTradePriorityPriceAndSuccessMinutes.prognosedTradeSuccessMinutes();
            }

            return new TradePriorityPriceAndSuccessMinutes(tradePriority, tradePrice, prognosedTradeSuccessMinutes);
        }
    }

    private static TradePriorityPriceAndSuccessMinutes getMaxBuyPriority(Item item, Integer buyBoundaryPrice, Integer priority) {
        TradePriorityPriceAndSuccessMinutes buyInstantlyPriority = getBuyTradePriority(buyBoundaryPrice, item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES, priority);
        TradePriorityPriceAndSuccessMinutes buyIn1hPriority = getBuyTradePriority(buyBoundaryPrice, item.getPriceToBuyIn1Hour(), 60, priority);
        TradePriorityPriceAndSuccessMinutes buyIn6hPriority = getBuyTradePriority(buyBoundaryPrice, item.getPriceToBuyIn6Hours(), 360, priority);
        TradePriorityPriceAndSuccessMinutes buyIn24hPriority = getBuyTradePriority(buyBoundaryPrice, item.getPriceToBuyIn24Hours(), 1440, priority);
        TradePriorityPriceAndSuccessMinutes buyIn168hPriority = getBuyTradePriority(buyBoundaryPrice, item.getPriceToBuyIn168Hours(), 10080, priority);

        return Stream.of(buyInstantlyPriority, buyIn1hPriority, buyIn6hPriority, buyIn24hPriority, buyIn168hPriority)
                .filter(tradePriorityPriceAndSuccessMinutes -> tradePriorityPriceAndSuccessMinutes.tradePriority() > 0)
                .max(Comparator.comparingLong(TradePriorityPriceAndSuccessMinutes::tradePriority))
                .orElse(new TradePriorityPriceAndSuccessMinutes(0, 0, 0));
    }

    private static TradePriorityPriceAndSuccessMinutes getBuyTradePriority(Integer buyBoundaryPrice, Integer price, Integer minutesToTrade,
                                                                           Integer priority) {
        long constant = Integer.MAX_VALUE;
        if (price != null && price <= buyBoundaryPrice && price > 0) {
            long tradePriority = (constant / getPriceFactor(price)) * getTimeFactor(minutesToTrade) * priority;
            return new TradePriorityPriceAndSuccessMinutes(tradePriority, price, minutesToTrade);
        } else {
            return new TradePriorityPriceAndSuccessMinutes(0, price == null ? 0 : price, minutesToTrade);
        }
    }

    private static TradePriorityPriceAndSuccessMinutes getMaxSellPriority(Item item, Integer sellBoundaryPrice, Integer priority) {
        TradePriorityPriceAndSuccessMinutes sellInstantlyPriority = getSellTradePriority(sellBoundaryPrice, item.getMinSellPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES, priority);
        TradePriorityPriceAndSuccessMinutes sellIn1hPriority = getSellTradePriority(sellBoundaryPrice, item.getPriceToSellIn1Hour(), 60, priority);
        TradePriorityPriceAndSuccessMinutes sellIn6hPriority = getSellTradePriority(sellBoundaryPrice, item.getPriceToSellIn6Hours(), 360, priority);
        TradePriorityPriceAndSuccessMinutes sellIn24hPriority = getSellTradePriority(sellBoundaryPrice, item.getPriceToSellIn24Hours(), 1440, priority);
        TradePriorityPriceAndSuccessMinutes sellIn168hPriority = getSellTradePriority(sellBoundaryPrice, item.getPriceToSellIn168Hours(), 10080, priority);

        return Stream.of(sellInstantlyPriority, sellIn1hPriority, sellIn6hPriority, sellIn24hPriority, sellIn168hPriority)
                .filter(tradePriorityPriceAndSuccessMinutes -> tradePriorityPriceAndSuccessMinutes.tradePriority() > 0)
                .max(Comparator.comparingLong(TradePriorityPriceAndSuccessMinutes::tradePriority))
                .orElse(new TradePriorityPriceAndSuccessMinutes(0, 0, 0));
    }

    private static TradePriorityPriceAndSuccessMinutes getSellTradePriority(Integer sellBoundaryPrice, Integer price, Integer minutesToTrade, Integer priority) {
        if (price != null && price >= sellBoundaryPrice && price > 0) {
            long tradePriority = getPriceFactor(price) * getTimeFactor(minutesToTrade) * priority;
            return new TradePriorityPriceAndSuccessMinutes(tradePriority, price, minutesToTrade);
        } else {
            return new TradePriorityPriceAndSuccessMinutes(0, price == null ? 0 : price, minutesToTrade);
        }
    }

    private static long getPriceFactor(int price) {
        return (long) Math.pow(price, TRADE_MANAGER_PRICE_POW_FACTOR);
    }

    private static long getTimeFactor(int minutesToTrade) {
        long constant = 43200; // minutes in a month
        return constant / (long) (Math.pow(minutesToTrade, TRADE_MANAGER_TIME_POW_FACTOR));
    }

    public String getItemId() {
        return item.getItemId();
    }

    public String getName() {
        return item.getName();
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

    private record TradePriorityPriceAndSuccessMinutes(long tradePriority, int price, int prognosedTradeSuccessMinutes) {
    }
}
