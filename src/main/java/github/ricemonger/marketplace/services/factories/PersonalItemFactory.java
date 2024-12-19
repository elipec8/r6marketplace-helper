package github.ricemonger.marketplace.services.factories;

import github.ricemonger.utils.DTOs.PersonalItem;
import github.ricemonger.utils.DTOs.TradeByFiltersManager;
import github.ricemonger.utils.DTOs.TradeByItemIdManager;
import github.ricemonger.utils.DTOs.items.UbiTrade;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.ItemFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class PersonalItemFactory {

    public Set<PersonalItem> getPersonalItemsForUser(Collection<TradeByFiltersManager> tradeByFiltersManagers,
                                                     Collection<TradeByItemIdManager> tradeByItemIdManagers,
                                                     Collection<UbiTrade> existingSellTrades,
                                                     Collection<UbiTrade> existingBuyTrades,
                                                     Collection<String> ownedItemsId,
                                                     Collection<Item> existingItems) {
        Set<PersonalItem> personalItems = new HashSet<>();
        personalItems.addAll(getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(
                tradeByFiltersManagers,
                existingSellTrades,
                existingBuyTrades,
                ownedItemsId,
                existingItems));
        personalItems.addAll(getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(
                tradeByItemIdManagers,
                existingSellTrades,
                existingBuyTrades,
                ownedItemsId,
                existingItems));

        return personalItems;
    }

    private Set<PersonalItem> getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(Collection<TradeByFiltersManager> tradeByFiltersManagers,
                                                                                                 Collection<UbiTrade> existingSellTrades,
                                                                                                 Collection<UbiTrade> existingBuyTrades,
                                                                                                 Collection<String> ownedItemsIds,
                                                                                                 Collection<Item> existingItems) {
        Set<PersonalItem> personalItems = new HashSet<>();

        if (tradeByFiltersManagers == null || tradeByFiltersManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return personalItems;
        }

        List<TradeByFiltersManager> sortedTradeByFiltersManagers =
                tradeByFiltersManagers.stream().filter(m -> m.getPriorityMultiplier() != null).sorted(Comparator.comparingInt(TradeByFiltersManager::getPriorityMultiplier)).toList();

        for (TradeByFiltersManager tradeByFiltersManager : sortedTradeByFiltersManagers) {
            personalItems.addAll(getItemsForCentralTradeManagerFromTradeByFiltersManager(
                    tradeByFiltersManager,
                    existingSellTrades,
                    existingBuyTrades,
                    ownedItemsIds,
                    existingItems));
        }
        return personalItems;
    }

    private Set<PersonalItem> getItemsForCentralTradeManagerFromTradeByFiltersManager(TradeByFiltersManager tradeByFiltersManager,
                                                                                      Collection<UbiTrade> existingSellTrades,
                                                                                      Collection<UbiTrade> existingBuyTrades,
                                                                                      Collection<String> ownedItemsIds,
                                                                                      Collection<Item> existingItems) {
        if (tradeByFiltersManager.getAppliedFilters() == null || tradeByFiltersManager.getAppliedFilters().isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(ItemFilter.filterItems(existingItems, tradeByFiltersManager.getAppliedFilters())
                    .stream()
                    .map(item -> {
                                int sellBoundaryPrice = Integer.MIN_VALUE;
                                int buyBoundaryPrice = Integer.MAX_VALUE;

                                boolean isOwned = ownedItemsIds.contains(item.getItemId());
                                UbiTrade existingTrade;

                                if (isOwned) {
                                    existingTrade = existingSellTrades.stream().filter(t -> t.getItemId().equals(item.getItemId())).findFirst().orElse(null);
                                } else {
                                    existingTrade = existingBuyTrades.stream().filter(t -> t.getItemId().equals(item.getItemId())).findFirst().orElse(null);
                                }

                                return new PersonalItem(
                                        item,
                                        sellBoundaryPrice,
                                        buyBoundaryPrice,
                                        tradeByFiltersManager.getMinDifferenceFromMedianPrice(),
                                        tradeByFiltersManager.getMinDifferenceFromMedianPricePercent(),
                                        tradeByFiltersManager.getTradeOperationType(),
                                        tradeByFiltersManager.getPriorityMultiplier(),
                                        isOwned,
                                        existingTrade != null,
                                        existingTrade
                                );
                            }
                    )
                    .toList());
        }
    }

    private Set<PersonalItem> getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(Collection<TradeByItemIdManager> tradeByItemIdManagers,
                                                                                                Collection<UbiTrade> existingSellTrades,
                                                                                                Collection<UbiTrade> existingBuyTrades,
                                                                                                Collection<String> ownedItemsIds,
                                                                                                Collection<Item> existingItems) {
        Set<PersonalItem> personalItems = new HashSet<>();

        if (tradeByItemIdManagers == null || tradeByItemIdManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return personalItems;
        }

        List<TradeByItemIdManager> sortedTradeByItemIdManagers =
                tradeByItemIdManagers.stream().filter(m -> m.getPriorityMultiplier() != null).sorted(Comparator.comparingInt(TradeByItemIdManager::getPriorityMultiplier)).toList();

        for (TradeByItemIdManager tradeByItemIdManager : sortedTradeByItemIdManagers) {
            PersonalItem personalItem = getItemForCentralTradeManagerFromTradeByItemIdManager(
                    tradeByItemIdManager,
                    existingSellTrades,
                    existingBuyTrades,
                    ownedItemsIds,
                    existingItems);
            if (personalItem != null) {
                personalItems.add(personalItem);
            }
        }
        return personalItems;
    }

    private PersonalItem getItemForCentralTradeManagerFromTradeByItemIdManager(TradeByItemIdManager tradeByItemIdManager,
                                                                               Collection<UbiTrade> existingSellTrades,
                                                                               Collection<UbiTrade> existingBuyTrades,
                                                                               Collection<String> ownedItemsIds,
                                                                               Collection<Item> existingItems) {
        Item item = existingItems.stream().filter(i -> i.getItemId().equals(tradeByItemIdManager.getItemId())).findFirst().orElse(null);
        if (item != null) {
            int minBuySellProfit = Integer.MIN_VALUE;
            int minBuySellProfitPercent = Integer.MIN_VALUE;

            boolean isOwned = ownedItemsIds.contains(item.getItemId());
            UbiTrade existingTrade;

            if (isOwned) {
                existingTrade = existingSellTrades.stream().filter(t -> t.getItemId().equals(item.getItemId())).findFirst().orElse(null);
            } else {
                existingTrade = existingBuyTrades.stream().filter(t -> t.getItemId().equals(item.getItemId())).findFirst().orElse(null);
            }

            return new PersonalItem(
                    item,
                    tradeByItemIdManager.getSellBoundaryPrice(),
                    tradeByItemIdManager.getBuyBoundaryPrice(),
                    minBuySellProfit,
                    minBuySellProfitPercent,
                    tradeByItemIdManager.getTradeOperationType(),
                    tradeByItemIdManager.getPriorityMultiplier(),
                    ownedItemsIds.contains(item.getItemId()),
                    existingTrade != null,
                    existingTrade
            );
        } else {
            return null;
        }
    }
}
