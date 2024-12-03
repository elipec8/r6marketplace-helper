package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByFiltersManager {
    private String name;
    private boolean enabled;
    private TradeOperationType tradeOperationType;
    private List<ItemFilter> appliedFilters;
    private Integer minBuySellProfit;
    private Integer minProfitPercent;
    private Integer priority;

    public static Set<ItemForCentralTradeManager> getItemsForCentralTradeManagerFromTradeByFiltersManagersByPriority(List<TradeByFiltersManager> tradeByFiltersManagers, Collection<Item> existingItems) {
        Set<ItemForCentralTradeManager> itemForCentralTradeManagers = new HashSet<>();

        if (tradeByFiltersManagers == null || tradeByFiltersManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return itemForCentralTradeManagers;
        }

        List<TradeByFiltersManager> sortedTradeByFiltersManagers =
                tradeByFiltersManagers.stream().filter(m -> m.priority != null).sorted(Comparator.comparingInt(TradeByFiltersManager::getPriority)).toList();

        for (TradeByFiltersManager tradeByFiltersManager : sortedTradeByFiltersManagers) {
            itemForCentralTradeManagers.addAll(tradeByFiltersManager.toItemForCentralTradeManagerDTOs(existingItems));
        }
        return itemForCentralTradeManagers;
    }

    public Set<ItemForCentralTradeManager> toItemForCentralTradeManagerDTOs(Collection<Item> existingItems) {
        if (this.getAppliedFilters() == null || this.getAppliedFilters().isEmpty()) {
            return new HashSet<>();
        } else {
            return new HashSet<>(ItemFilter.filterItems(existingItems, this.getAppliedFilters())
                    .stream()
                    .map(item -> new ItemForCentralTradeManager(item, this))
                    .toList());
        }
    }

    public String toString() {
        String sb = "Trade By Item Filter Manager: \n" +
                    "Name: " + name + "\n" +
                    "Enabled: " + enabled + "\n" +
                    "Trade type: " + tradeOperationType + "\n";
        if (appliedFilters != null) {
            sb = sb + "Applied filters' names: " + appliedFilters.stream().map(ItemFilter::getName).reduce((a, b) -> a + ", " + b).orElse("") + "\n";
        } else {
            sb = sb + "Applied filters: null\n";
        }
        sb = sb + "Min profit: " + minBuySellProfit + "\n" +
             "Min profit percent: " + minProfitPercent + "\n" +
             "Priority: " + priority + "\n";
        return sb;
    }
}
