package github.ricemonger.utils.DTOs;

import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManager;
import github.ricemonger.utils.enums.TradeOperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeByItemIdManager {
    private TradeOperationType tradeOperationType;
    private String itemId;
    private boolean enabled;
    private Integer sellBoundaryPrice;
    private Integer buyBoundaryPrice;
    private Integer priority;

    public static Set<ItemForCentralTradeManager> getItemsForCentralTradeManagerFromTradeByItemIdManagersByPriority(List<TradeByItemIdManager> tradeByItemIdManagers,
                                                                                                                    Collection<Item> existingItems) {
        Set<ItemForCentralTradeManager> itemForCentralTradeManagers = new HashSet<>();

        if (tradeByItemIdManagers == null || tradeByItemIdManagers.isEmpty() || existingItems == null || existingItems.isEmpty()) {
            return itemForCentralTradeManagers;
        }

        List<TradeByItemIdManager> sortedTradeByItemIdManagers = tradeByItemIdManagers.stream().filter(m -> m.priority != null).sorted(Comparator.comparingInt(TradeByItemIdManager::getPriority)).toList();

        for (TradeByItemIdManager tradeByItemIdManager : sortedTradeByItemIdManagers) {
            ItemForCentralTradeManager itemForCentralTradeManager = tradeByItemIdManager.toItemForCentralTradeManagerDtoOrNull(existingItems);
            if (itemForCentralTradeManager != null) {
                itemForCentralTradeManagers.add(itemForCentralTradeManager);
            }
        }
        return itemForCentralTradeManagers;
    }

    public ItemForCentralTradeManager toItemForCentralTradeManagerDtoOrNull(Collection<Item> existingItems) {
        Item item = existingItems.stream().filter(i -> i.getItemId().equals(this.itemId)).findFirst().orElse(null);
        if (item != null) {
            return new ItemForCentralTradeManager(item, this);
        } else {
            return null;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trade Manager for one item: \n");
        sb.append("Trade type: ").append(tradeOperationType).append("\n");
        sb.append("Item id: ").append(itemId).append("\n");
        sb.append("Enabled: ").append(enabled).append("\n");

        if (tradeOperationType == TradeOperationType.SELL) {
            sb.append("Boundary price: ").append(sellBoundaryPrice).append("\n");
        } else if (tradeOperationType == TradeOperationType.BUY) {
            sb.append("Boundary price: ").append(buyBoundaryPrice).append("\n");
        } else if (tradeOperationType == TradeOperationType.BUY_AND_SELL || tradeOperationType == null) {
            sb.append("Boundary Sell price: ").append(sellBoundaryPrice).append("\n");
            sb.append("Boundary Buy price: ").append(buyBoundaryPrice).append("\n");
        }

        sb.append("Priority: ").append(priority).append("\n");

        return sb.toString();
    }
}
