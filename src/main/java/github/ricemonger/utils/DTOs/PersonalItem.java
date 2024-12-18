package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.ItemEntityDTO;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
public class PersonalItem {
    private ItemEntityDTO item;
    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;
    private Integer minMedianPriceDifference;
    private Integer minMedianPriceDifferencePercent;
    private TradeOperationType tradeOperationType;

    private Integer priorityMultiplier;
    private Boolean isOwned;

    private Boolean tradeAlreadyExists;
    private UbiTrade existingTrade;

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
        if (!(obj instanceof PersonalItem itemForTradeDTO)) {
            return false;
        }
        return Objects.equals(item, itemForTradeDTO.item) && Objects.equals(tradeOperationType, itemForTradeDTO.tradeOperationType);
    }

    public int getProposedPaymentPrice() {
        return existingTrade.getProposedPaymentPrice();
    }

    public String getItemName() {
        return item.getName();
    }

    public String getTradeId() {
        if (existingTrade == null) {
            return null;
        } else {
            return existingTrade.getTradeId();
        }
    }
}
