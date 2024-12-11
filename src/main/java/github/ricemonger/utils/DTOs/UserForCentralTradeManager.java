package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.ItemResaleLockWithUbiAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForCentralTradeManager {
    private Long id;

    private UbiAccountStats ubiAccountStats;

    private String ubiSessionId;
    private String ubiSpaceId;
    private String ubiAuthTicket;
    private String ubiTwoFactorAuthTicket;
    private String ubiRememberDeviceTicket;
    private String ubiRememberMeTicket;

    private String chatId;
    private Boolean privateNotificationsEnabledFlag;

    private List<TradeByFiltersManager> tradeByFiltersManagers = new ArrayList<>();

    private List<TradeByItemIdManager> tradeByItemIdManagers = new ArrayList<>();

    public String getUbiProfileId() {
        return ubiAccountStats.getUbiProfileId();
    }

    public Integer getSoldIn24h() {
        return ubiAccountStats.getSoldIn24h();
    }

    public Integer getBoughtIn24h() {
        return ubiAccountStats.getBoughtIn24h();
    }

    public Integer getCreditAmount() {
        return ubiAccountStats.getCreditAmount();
    }

    public List<String> getOwnedItemsIds() {
        return ubiAccountStats.getOwnedItemsIds();
    }

    public List<ItemResaleLockWithUbiAccount> getResaleLocks() {
        return ubiAccountStats.getResaleLocks();
    }

    public List<UbiTrade> getCurrentBuyTrades() {
        return ubiAccountStats.getCurrentBuyTrades();
    }

    public List<UbiTrade> getCurrentSellTrades() {
        return ubiAccountStats.getCurrentSellTrades();
    }

    public Set<ItemForCentralTradeManager> getItemForCentralTradeManagerFromTradeManagersByPriority(Collection<Item> existingItems,
                                                                                                    int feePercentage) {
        Set<ItemForCentralTradeManager> itemForCentralTradeManagers = new HashSet<>();
        itemForCentralTradeManagers.addAll(getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(tradeByFiltersManagers,
                existingItems, feePercentage));
        itemForCentralTradeManagers.addAll(getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(tradeByItemIdManagers,
                existingItems, feePercentage));

        return itemForCentralTradeManagers;
    }

    private Set<ItemForCentralTradeManager> getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(List<TradeByItemIdManager> tradeByItemIdManagers,
                                                                                                              Collection<Item> existingItems,
                                                                                                              int feePercentage) {
        Set<ItemForCentralTradeManager> itemForCentralTradeManagers = new HashSet<>();

        if (tradeByItemIdManagers == null || tradeByItemIdManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return itemForCentralTradeManagers;
        }

        List<TradeByItemIdManager> sortedTradeByItemIdManagers =
                tradeByItemIdManagers.stream().filter(m -> m.getPriority() != null).sorted(Comparator.comparingInt(TradeByItemIdManager::getPriority)).toList();

        for (TradeByItemIdManager tradeByItemIdManager : sortedTradeByItemIdManagers) {
            ItemForCentralTradeManager itemForCentralTradeManager = getItemForCentralTradeManagerFromTradeByItemIdManager(tradeByItemIdManager,
                    existingItems, feePercentage);
            if (itemForCentralTradeManager != null) {
                itemForCentralTradeManagers.add(itemForCentralTradeManager);
            }
        }
        return itemForCentralTradeManagers;
    }

    private ItemForCentralTradeManager getItemForCentralTradeManagerFromTradeByItemIdManager(TradeByItemIdManager tradeByItemIdManager,
                                                                                             Collection<Item> existingItems,
                                                                                             int feePercentage) {
        Item item = existingItems.stream().filter(i -> i.getItemId().equals(tradeByItemIdManager.getItemId())).findFirst().orElse(null);
        if (item != null) {
            return new ItemForCentralTradeManager(item, tradeByItemIdManager, feePercentage, getOwnedItemsIds());
        } else {
            return null;
        }
    }

    private Set<ItemForCentralTradeManager> getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(List<TradeByFiltersManager> tradeByFiltersManagers, Collection<Item> existingItems,
                                                                                                               int feePercentage) {
        Set<ItemForCentralTradeManager> itemForCentralTradeManagers = new HashSet<>();

        if (tradeByFiltersManagers == null || tradeByFiltersManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return itemForCentralTradeManagers;
        }

        List<TradeByFiltersManager> sortedTradeByFiltersManagers =
                tradeByFiltersManagers.stream().filter(m -> m.getPriority() != null).sorted(Comparator.comparingInt(TradeByFiltersManager::getPriority)).toList();

        for (TradeByFiltersManager tradeByFiltersManager : sortedTradeByFiltersManagers) {
            itemForCentralTradeManagers.addAll(getItemsForCentralTradeManagerFromTradeByFiltersManager(tradeByFiltersManager, existingItems, feePercentage));
        }
        return itemForCentralTradeManagers;
    }

    private Set<ItemForCentralTradeManager> getItemsForCentralTradeManagerFromTradeByFiltersManager(TradeByFiltersManager tradeByFiltersManager,
                                                                                                    Collection<Item> existingItems,
                                                                                                    int feePercentage) {
        if (tradeByFiltersManager.getAppliedFilters() == null || tradeByFiltersManager.getAppliedFilters().isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(ItemFilter.filterItems(existingItems, tradeByFiltersManager.getAppliedFilters())
                    .stream()
                    .map(item -> new ItemForCentralTradeManager(item, tradeByFiltersManager, feePercentage, getOwnedItemsIds()))
                    .toList());
        }
    }
}
