package github.ricemonger.marketplace.services;

import github.ricemonger.utils.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CentralTradeManager {

    private final UserService userService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> existingItems = itemService.getAllItems();

        List<TradingUser> toManage = userService.getAllTradingUsers();

        List<CentralTradeManagerCommand> commands = new ArrayList<>();

        for (TradingUser tradingUser : toManage) {
            commands.addAll(createCentralTradeManagerCommandsForUser(tradingUser, configTrades, existingItems));
        }
    }

    private List<CentralTradeManagerCommand> createCentralTradeManagerCommandsForUser(TradingUser tradingUser, ConfigTrades configTrades, Collection<Item> existingItems) {
        Set<ItemForTradeDTO> itemsFromFiltersManagerForTradeDTOS = getTradingItemsFromTradeByFiltersManagers(tradingUser.getTradeByFiltersManagers(), existingItems);

        Set<ItemForTradeDTO> itemFromItemForTradeDTOIdManagers = getTradingItemsFromTradeByItemIdManagers(tradingUser.getTradeByItemIdManagers(), existingItems);

        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();
        itemForTradeDTOS.addAll(itemsFromFiltersManagerForTradeDTOS);
        itemForTradeDTOS.addAll(itemFromItemForTradeDTOIdManagers);

        return null;
    }


    private Set<ItemForTradeDTO> getTradingItemsFromTradeByFiltersManagers(List<TradeByFiltersManager> tradeByFiltersManagers, Collection<Item> existingItems) {
        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByFiltersManager tradeByFiltersManager : tradeByFiltersManagers) {
            itemForTradeDTOS.addAll(
                    ItemFilter.filterItems(existingItems, tradeByFiltersManager.getAppliedFilters())
                            .stream()
                            .map(item -> new ItemForTradeDTO(item, tradeByFiltersManager.getTradeManagingType(), tradeByFiltersManager.getPriority()))
                            .toList());
        }
        return itemForTradeDTOS;
    }

    private Set<ItemForTradeDTO> getTradingItemsFromTradeByItemIdManagers(List<TradeByItemIdManager> tradeByItemIdManagers,
                                                                          Collection<Item> existingItems) {
        Set<ItemInSet> existingItemsSet = existingItems.stream().map(item -> (ItemInSet) item).collect(Collectors.toSet());

        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByItemIdManager tradeByItemIdManager : tradeByItemIdManagers) {
            Item item = getItemFromSetByItemId(existingItemsSet, tradeByItemIdManager.getItemId());
            if (item != null) {
                itemForTradeDTOS.add(new ItemForTradeDTO(item, tradeByItemIdManager.getTradeManagingType(), tradeByItemIdManager.getPriority()));
            }
        }
        return itemForTradeDTOS;
    }

    private Item getItemFromSetByItemId(Set<ItemInSet> existingItems, String itemId) {
        return existingItems.stream().filter(item -> item.getItemId().equals(itemId)).findFirst().orElse(null);
    }
}
