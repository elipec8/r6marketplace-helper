package github.ricemonger.trades_manager.services.factories;

import github.ricemonger.trades_manager.services.DTOs.PersonalItem;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalBuyTrade;
import github.ricemonger.trades_manager.services.DTOs.PotentialPersonalSellTrade;
import github.ricemonger.trades_manager.services.DTOs.PrioritizedUbiTrade;
import github.ricemonger.trades_manager.services.PotentialTradeStatsService;
import github.ricemonger.utils.DTOs.common.PotentialTradeStats;
import github.ricemonger.utils.DTOs.common.PrioritizedPotentialTradeStats;
import github.ricemonger.utils.DTOs.personal.ItemResaleLock;
import github.ricemonger.utils.DTOs.personal.UbiTrade;
import github.ricemonger.utils.enums.TradeOperationType;
import github.ricemonger.utils.services.calculators.TradePriorityExpressionDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static github.ricemonger.utils.enums.TradeCategory.Buy;
import static github.ricemonger.utils.enums.TradeCategory.Sell;

@Component
@RequiredArgsConstructor
public class PotentialTradeFactory {

    private final TradePriorityExpressionDeserializer tradePriorityExpressionDeserializer;

    private final PotentialTradeStatsService potentialTradeStatsService;

    public List<PotentialPersonalSellTrade> getResultingPersonalSellTrades(String sellTradePriorityExpression, Collection<PersonalItem> personalItems, Collection<? extends ItemResaleLock> resaleLocks, Integer soldIn24h, int sellSlots, int sellLimit) {

        List<PotentialPersonalSellTrade> filteredPersonalSellTrades = getFilteredPotentialSellTradesForUser(sellTradePriorityExpression, personalItems, resaleLocks);

        List<PotentialPersonalSellTrade> resultingPersonalSellTrades = new LinkedList<>();
        int slotsLeft = sellSlots;
        int limitLeft = sellLimit - soldIn24h;

        for (PotentialPersonalSellTrade sellTrade : filteredPersonalSellTrades.stream().sorted().toList()) {

            if (slotsLeft <= 0) {
                break;
            }

            boolean resultHaveSameItemTrade = resultingPersonalSellTrades.stream().anyMatch(trade -> trade.getItemId().equals(sellTrade.getItemId()));

            if (resultHaveSameItemTrade || (limitLeft <= 0 && !sellTrade.tradeForItemAlreadyExists())) {
                continue;
            } else if (sellTrade.tradeForItemAlreadyExists()) {
                slotsLeft--;
                resultingPersonalSellTrades.add(sellTrade);
            } else if (limitLeft > 0) {
                slotsLeft--;
                limitLeft--;
                resultingPersonalSellTrades.add(sellTrade);
            } else {
                continue;
            }
        }

        return resultingPersonalSellTrades;
    }

    public List<PotentialPersonalSellTrade> getFilteredPotentialSellTradesForUser(String sellTradePriorityExpression,
                                                                                  Collection<PersonalItem> personalItems,
                                                                                  Collection<? extends ItemResaleLock> resaleLocks){
        List<PotentialPersonalSellTrade> potentialPersonalSellTrades = new ArrayList<>();
        for (PersonalItem personalItem : personalItems) {
            boolean itemIsNotOwned = !personalItem.getIsOwned();
            if (itemIsNotOwned) {
                continue;
            }
            boolean itemResaleIsLocked = resaleLocks.stream().anyMatch(resaleLock -> resaleLock.getItemId().equals(personalItem.getItemId()));
            if (itemResaleIsLocked) {
                continue;
            }
            boolean wrongTradeOperationType = personalItem.getTradeOperationType() == null || personalItem.getTradeOperationType() == TradeOperationType.BUY;
            if (wrongTradeOperationType) {
                continue;
            }

            List<PotentialTradeStats> potentialSellTradesStats = potentialTradeStatsService.getPotentialSellTradesStatsOfItem(personalItem.getItem());
            for (PotentialTradeStats potentialTradeStats : potentialSellTradesStats) {
                if (!potentialTradeStats.isValid()) {
                    continue;
                }
                boolean sellBoundaryPriceIsExceeded = personalItem.getSellBoundaryPrice() != null && potentialTradeStats.getPrice() < personalItem.getSellBoundaryPrice();
                if (sellBoundaryPriceIsExceeded) {
                    continue;
                }

                int medianPriceDifference;
                int monthMedianPrice;
                if (personalItem.getMonthMedianPrice() == null || personalItem.getMonthMedianPrice() <= 0) {
                    monthMedianPrice = 1;
                    medianPriceDifference = potentialTradeStats.getPrice();
                } else {
                    monthMedianPrice = personalItem.getMonthMedianPrice();
                    medianPriceDifference = (potentialTradeStats.getPrice() - monthMedianPrice);
                }

                boolean minMedianPriceDifferenceIsExceeded = personalItem.getMinMedianPriceDifference() != null && medianPriceDifference < personalItem.getMinMedianPriceDifference();
                if (minMedianPriceDifferenceIsExceeded) {
                    continue;
                }

                boolean minMedianPriceDifferencePercentIsExceeded = personalItem.getMinMedianPriceDifferencePercent() != null && (medianPriceDifference * 100) / monthMedianPrice < personalItem.getMinMedianPriceDifferencePercent();
                if (minMedianPriceDifferencePercentIsExceeded) {
                    continue;
                }

                Long tradePriority = tradePriorityExpressionDeserializer.calculateTradePriority(sellTradePriorityExpression, personalItem.getItem(), potentialTradeStats.getPrice(), potentialTradeStats.getTime());

                potentialPersonalSellTrades.add(new PotentialPersonalSellTrade(personalItem, new PrioritizedPotentialTradeStats(potentialTradeStats, tradePriority)));
            }
        }

        return potentialPersonalSellTrades;
    }

    public List<PotentialPersonalBuyTrade> getResultingPersonalBuyTrades(String buyTradePriorityExpression, Collection<PersonalItem> personalItems,
                                                                         Integer creditAmount, Integer boughtIn24h, int buySlots, int buyLimit) {
        List<PotentialPersonalBuyTrade> resultingPersonalBuyTrades = new LinkedList<>();
        int slotsLeft = buySlots;
        int limitLeft = buyLimit - boughtIn24h;
        int creditLeft = creditAmount;

        List<PotentialPersonalBuyTrade> filteredPersonalBuyTrades = getFilteredPotentialBuyTradesForUser(buyTradePriorityExpression,personalItems);

        for (PotentialPersonalBuyTrade buyTrade : filteredPersonalBuyTrades.stream().sorted().toList()) {

            if (slotsLeft <= 0) {
                break;
            }

            boolean resultHaveSameItemTrade = resultingPersonalBuyTrades.stream().anyMatch(trade -> trade.getItemId().equals(buyTrade.getItemId()));

            if (resultHaveSameItemTrade || (limitLeft <= 0 && !buyTrade.tradeForItemAlreadyExists())) {
                continue;
            } else if (buyTrade.tradeForItemAlreadyExists()) {
                int newPrice = buyTrade.getNewPrice();
                int oldPrice = buyTrade.getOldPrice();
                int prognosedCreditsAmount = creditLeft - newPrice + oldPrice;

                if (prognosedCreditsAmount >= 0) {
                    slotsLeft--;
                    creditLeft = prognosedCreditsAmount;
                    resultingPersonalBuyTrades.add(buyTrade);
                } else {
                    continue;
                }
            } else if (limitLeft > 0 && creditLeft - buyTrade.getNewPrice() >= 0) {
                slotsLeft--;
                limitLeft--;
                creditLeft -= buyTrade.getNewPrice();
                resultingPersonalBuyTrades.add(buyTrade);
            } else {
                continue;
            }
        }

        return resultingPersonalBuyTrades;
    }

    public List<PotentialPersonalBuyTrade> getFilteredPotentialBuyTradesForUser(String buyTradePriorityExpression,
                                                                                Collection<PersonalItem> personalItems) {
        List<PotentialPersonalBuyTrade> potentialPersonalBuyTrades = new ArrayList<>();
        for (PersonalItem personalItem : personalItems) {
            boolean itemIsOwned = personalItem.getIsOwned();
            if (itemIsOwned) {
                continue;
            }
            boolean wrongTradeOperationType = personalItem.getTradeOperationType() == null || personalItem.getTradeOperationType() == TradeOperationType.SELL;
            if (wrongTradeOperationType) {
                continue;
            }

            List<PotentialTradeStats> potentialBuyTradesStats = potentialTradeStatsService.getPotentialBuyTradesStatsOfItem(personalItem.getItem());
            for (PotentialTradeStats potentialTradeStats : potentialBuyTradesStats) {
                if (!potentialTradeStats.isValid()) {
                    continue;
                }
                if (personalItem.getBuyBoundaryPrice() != null && potentialTradeStats.getPrice() > personalItem.getBuyBoundaryPrice()) {
                    continue;
                }

                int medianPriceDifference;
                int monthMedianPrice;
                if (personalItem.getMonthMedianPrice() == null || personalItem.getMonthMedianPrice() <= 0) {
                    monthMedianPrice = 1;
                    medianPriceDifference = potentialTradeStats.getPrice();
                } else {
                    monthMedianPrice = personalItem.getMonthMedianPrice();
                    medianPriceDifference = (monthMedianPrice - potentialTradeStats.getPrice());
                }

                if (personalItem.getMinMedianPriceDifference() != null && medianPriceDifference < personalItem.getMinMedianPriceDifference()) {
                    continue;
                }
                if (personalItem.getMinMedianPriceDifferencePercent() != null && (medianPriceDifference * 100) / monthMedianPrice < personalItem.getMinMedianPriceDifferencePercent()) {
                    continue;
                }

                Long tradePriority = tradePriorityExpressionDeserializer.calculateTradePriority(buyTradePriorityExpression, personalItem.getItem(), potentialTradeStats.getPrice(), potentialTradeStats.getTime());

                potentialPersonalBuyTrades.add(new PotentialPersonalBuyTrade(personalItem, new PrioritizedPotentialTradeStats(potentialTradeStats, tradePriority)));
            }
        }

        return potentialPersonalBuyTrades;
    }

    public List<PrioritizedUbiTrade> prioritizeCurrentSellTrades(String sellTradePriorityExpression, List<UbiTrade> currentSellTrades) {

    }

    public List<PrioritizedUbiTrade> prioritizeCurrentBuyTrades(String buyTradePriorityExpression, List<UbiTrade> currentBuyTrades) {

    }
}
