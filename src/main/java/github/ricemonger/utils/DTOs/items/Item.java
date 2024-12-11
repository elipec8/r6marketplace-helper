package github.ricemonger.utils.DTOs.items;

import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.ItemType;
import github.ricemonger.utils.enums.TradeCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static github.ricemonger.marketplace.services.CentralTradeManager.TRADE_MANAGER_FIXED_RATE_MINUTES;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item implements ItemMainFieldsI, ItemHistoryFieldsI {
    private String itemId;
    private String assetUrl;
    private String name;
    private List<String> tags;

    private ItemRarity rarity;

    private ItemType type;

    private Integer maxBuyPrice;
    private Integer buyOrdersCount;

    private Integer minSellPrice;
    private Integer sellOrdersCount;

    private LocalDateTime lastSoldAt;
    private Integer lastSoldPrice;

    //history fields

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

    public Item(String itemId) {
        this.itemId = itemId;
    }

    public Item(Item item) {
        this.itemId = item.getItemId();
        this.assetUrl = item.getAssetUrl();
        this.name = item.getName();
        if (item.getTags() == null) {
            this.tags = null;
        } else {
            this.tags = item.getTags().subList(0, item.getTags().size());
        }
        this.rarity = item.getRarity();
        this.type = item.getType();
        this.maxBuyPrice = item.getMaxBuyPrice();
        this.buyOrdersCount = item.getBuyOrdersCount();
        this.minSellPrice = item.getMinSellPrice();
        this.sellOrdersCount = item.getSellOrdersCount();
        this.lastSoldAt = item.getLastSoldAt();
        this.lastSoldPrice = item.getLastSoldPrice();
        this.monthAveragePrice = item.getMonthAveragePrice();
        this.monthMedianPrice = item.getMonthMedianPrice();
        this.monthMaxPrice = item.getMonthMaxPrice();
        this.monthMinPrice = item.getMonthMinPrice();
        this.monthSalesPerDay = item.getMonthSalesPerDay();
        this.dayAveragePrice = item.getDayAveragePrice();
        this.dayMedianPrice = item.getDayMedianPrice();
        this.dayMaxPrice = item.getDayMaxPrice();
        this.dayMinPrice = item.getDayMinPrice();
        this.daySales = item.getDaySales();
        this.priceToSellIn1Hour = item.getPriceToSellIn1Hour();
        this.priceToSellIn6Hours = item.getPriceToSellIn6Hours();
        this.priceToSellIn24Hours = item.getPriceToSellIn24Hours();
        this.priceToSellIn168Hours = item.getPriceToSellIn168Hours();
        this.priceToBuyIn1Hour = item.getPriceToBuyIn1Hour();
        this.priceToBuyIn6Hours = item.getPriceToBuyIn6Hours();
        this.priceToBuyIn24Hours = item.getPriceToBuyIn24Hours();
        this.priceToBuyIn168Hours = item.getPriceToBuyIn168Hours();
    }

    public Item(ItemMainFieldsI mainFields) {
        setMainFields(mainFields);
    }

    public Item(ItemHistoryFieldsI historyFields) {
        setHistoryFields(historyFields);
    }

    public Item(ItemMainFieldsI mainFields, ItemHistoryFieldsI historyFields) {
        setMainFields(mainFields);
        setHistoryFields(historyFields);
    }

    public Integer getPrognosedTradeSuccessMinutes(Integer proposedPaymentPrice, TradeCategory category) {
        if (category == TradeCategory.Buy) {
            if (proposedPaymentPrice >= this.minSellPrice) {
                return TRADE_MANAGER_FIXED_RATE_MINUTES;
            } else if (proposedPaymentPrice >= this.priceToBuyIn1Hour) {
                return 60;
            } else if (proposedPaymentPrice >= this.priceToBuyIn6Hours) {
                return 360;
            } else if (proposedPaymentPrice >= this.priceToBuyIn24Hours) {
                return 1440;
            } else if (proposedPaymentPrice >= this.priceToBuyIn168Hours) {
                return 10080;
            } else if (proposedPaymentPrice >= this.monthMinPrice) {
                return 43200;
            } else {
                return 0;
            }
        } else if (category == TradeCategory.Sell) {
            if (proposedPaymentPrice <= this.maxBuyPrice) {
                return TRADE_MANAGER_FIXED_RATE_MINUTES;
            } else if (proposedPaymentPrice <= this.priceToSellIn1Hour) {
                return 60;
            } else if (proposedPaymentPrice <= this.priceToSellIn6Hours) {
                return 360;
            } else if (proposedPaymentPrice <= this.priceToSellIn24Hours) {
                return 1440;
            } else if (proposedPaymentPrice <= this.priceToSellIn168Hours) {
                return 10080;
            } else if (proposedPaymentPrice <= this.monthMaxPrice) {
                return 43200;
            } else {
                return 0;
            }
        } else {
            log.error("Trade category is Unknown is not set for item with id: " + itemId);
            return 0;
        }
    }

    public boolean isFullyEqualTo(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        return itemMainFieldsAreEqual(item) && itemHistoryFieldsAreEqual(item);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item item)) {
            return false;
        }
        if (item.getItemId() == null || this.getItemId() == null) {
            return false;
        }
        return item.getItemId().equals(this.getItemId());
    }

    public int hashCode() {
        return this.getItemId().hashCode();
    }
}
