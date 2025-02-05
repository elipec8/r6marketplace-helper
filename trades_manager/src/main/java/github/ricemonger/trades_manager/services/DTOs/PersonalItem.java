package github.ricemonger.trades_manager.services.DTOs;

import github.ricemonger.utils.DTOs.common.Item;
import github.ricemonger.utils.DTOs.common.PotentialTradePriceAndTimeStats;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.enums.ItemRarity;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static github.ricemonger.utils.services.calculators.ItemTradeTimeCalculator.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalItem {
    private Item item;
    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;
    private Integer minMedianPriceDifference;
    private Integer minMedianPriceDifferencePercent;
    private TradeOperationType tradeOperationType;

    private Integer priorityMultiplier;
    private Boolean isOwned;

    private Boolean tradeAlreadyExists;
    private Trade existingTrade;

    private Long priorityToSellByMaxBuyPrice;
    private Long priorityToSellByNextFancySellPrice;

    private Long priorityToBuyByMinSellPrice;

    private Long priorityToBuyIn1Hour;
    private Long priorityToBuyIn6Hours;
    private Long priorityToBuyIn24Hours;
    private Long priorityToBuyIn168Hours;
    private Long priorityToBuyIn720Hours;

    public String getItemId() {
        return item == null ? null : item.getItemId();
    }

    public String getName() {
        return item == null ? null : item.getName();
    }

    public Integer getMonthMedianPrice() {
        return item == null ? null : item.getMonthMedianPrice();
    }

    public Integer getProposedPaymentPrice() {
        return existingTrade == null ? null : existingTrade.getProposedPaymentPrice();
    }

    public String getTradeId() {
        return existingTrade == null ? null : existingTrade.getTradeId();
    }

    public ItemRarity getRarity() {
        return item == null ? null : item.getRarity();
    }

    public LocalDateTime getLastSoldAt() {
        return item == null ? null : item.getLastSoldAt();
    }

    public List<PotentialTradeStats> getPotentialSellTradesStatsOfItem() {
        List<PotentialTradeStats> result = new ArrayList<>();

        if (priorityToSellByNextFancySellPrice != null) {
            PotentialTradePriceAndTimeStats priceAndTime = itemTradeTimeCalculator.calculatePriceAndTimeForNextFancySellPriceSale(item);
            result.add(new PotentialTradeStats(priceAndTime.price(), priceAndTime.time(), item.getPriorityToSellByNextFancySellPrice()));
        }

        if (item.getPriorityToSellByMaxBuyPrice() != null && item.getMaxBuyPrice() != null) {
            result.add(new PotentialTradeStats(item.getMaxBuyPrice(), TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToSellByMaxBuyPrice()));
        }

        return result;
    }

    public List<PotentialTradeStats> getPotentialBuyTradesStatsOfItem() {
        List<PotentialTradeStats> result = new ArrayList<>();

        if (item.getPriorityToBuyByMinSellPrice() != null) {
            int price;
            if (item.getMinSellPrice() == null || item.getMinSellPrice() <= 0) {
                price = commonValuesService.getMinimumPriceByRarity(item.getRarity());
            } else {
                price = item.getMinSellPrice();
            }
            result.add(new PotentialTradeStats(price, TRADE_MANAGER_FIXED_RATE_MINUTES, item.getPriorityToBuyByMinSellPrice()));
        }
        if (item.getPriceToBuyIn1Hour() != null && item.getPriorityToBuyIn1Hour() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn1Hour(), MINUTES_IN_AN_HOUR, item.getPriorityToBuyIn1Hour()));
        }
        if (item.getPriceToBuyIn6Hours() != null && item.getPriorityToBuyIn6Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn6Hours(), MINUTES_IN_6_HOURS, item.getPriorityToBuyIn6Hours()));
        }
        if (item.getPriceToBuyIn24Hours() != null && item.getPriorityToBuyIn24Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn24Hours(), MINUTES_IN_A_DAY, item.getPriorityToBuyIn24Hours()));
        }
        if (item.getPriceToBuyIn168Hours() != null && item.getPriorityToBuyIn168Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn168Hours(), MINUTES_IN_A_WEEK, item.getPriorityToBuyIn168Hours()));
        }
        if (item.getPriceToBuyIn720Hours() != null && item.getPriorityToBuyIn720Hours() != null) {
            result.add(new PotentialTradeStats(item.getPriceToBuyIn720Hours(), MINUTES_IN_A_MONTH, item.getPriorityToBuyIn720Hours()));
        }

        return result;
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
        return Objects.equals(item, itemForTradeDTO.item) &&
               Objects.equals(tradeOperationType, itemForTradeDTO.tradeOperationType) &&
               Objects.equals(sellBoundaryPrice, itemForTradeDTO.sellBoundaryPrice) &&
               Objects.equals(buyBoundaryPrice, itemForTradeDTO.buyBoundaryPrice) &&
               Objects.equals(minMedianPriceDifference, itemForTradeDTO.minMedianPriceDifference) &&
               Objects.equals(minMedianPriceDifferencePercent, itemForTradeDTO.minMedianPriceDifferencePercent);
    }

    public boolean isFullyEqual(PersonalItem other) {
        if (other == null) {
            return false;
        }

        boolean itemsAreEqual = item == null && other.item == null || item != null && item.isFullyEquals(other.item);

        boolean existingTradesAreEqual =
                existingTrade == null && other.existingTrade == null || existingTrade != null && existingTrade.isFullyEqual(other.existingTrade);

        return itemsAreEqual &&
               Objects.equals(sellBoundaryPrice, other.sellBoundaryPrice) &&
               Objects.equals(buyBoundaryPrice, other.buyBoundaryPrice) &&
               Objects.equals(minMedianPriceDifference, other.minMedianPriceDifference) &&
               Objects.equals(minMedianPriceDifferencePercent, other.minMedianPriceDifferencePercent) &&
               Objects.equals(tradeOperationType, other.tradeOperationType) &&
               Objects.equals(priorityMultiplier, other.priorityMultiplier) &&
               Objects.equals(isOwned, other.isOwned) &&
               Objects.equals(tradeAlreadyExists, other.tradeAlreadyExists) &&
               existingTradesAreEqual &&
               Objects.equals(priorityToSellByMaxBuyPrice, other.priorityToSellByMaxBuyPrice) &&
               Objects.equals(priorityToSellByNextFancySellPrice, other.priorityToSellByNextFancySellPrice) &&
               Objects.equals(priorityToBuyByMinSellPrice, other.priorityToBuyByMinSellPrice) &&
               Objects.equals(priorityToBuyIn1Hour, other.priorityToBuyIn1Hour) &&
               Objects.equals(priorityToBuyIn6Hours, other.priorityToBuyIn6Hours) &&
               Objects.equals(priorityToBuyIn24Hours, other.priorityToBuyIn24Hours) &&
               Objects.equals(priorityToBuyIn168Hours, other.priorityToBuyIn168Hours) &&
               Objects.equals(priorityToBuyIn720Hours, other.priorityToBuyIn720Hours);
    }
}
