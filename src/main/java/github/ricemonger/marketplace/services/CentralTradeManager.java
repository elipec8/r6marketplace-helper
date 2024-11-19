package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.ItemForFastEquals;
import github.ricemonger.utils.DTOs.items.ItemForTradeDTO;
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

        List<Item> existingItemMainFields = itemService.getAllItems();

        List<TradingUser> toManage = userService.getAllTradingUsers();

        List<CentralTradeManagerCommand> commands = new ArrayList<>();

        for (TradingUser tradingUser : toManage) {
            commands.addAll(createCentralTradeManagerCommandsForUser(tradingUser, configTrades, existingItemMainFields));
        }
    }

    private List<CentralTradeManagerCommand> createCentralTradeManagerCommandsForUser(TradingUser tradingUser, ConfigTrades configTrades, Collection<Item> existingItemMainFields) {
        Set<ItemForTradeDTO> itemsFromFiltersManagerForTradeDTOS = getTradingItemsFromTradeByFiltersManagers(tradingUser.getTradeByFiltersManagers(), existingItemMainFields);

        Set<ItemForTradeDTO> itemFromItemForTradeDTOIdManagers = getTradingItemsFromTradeByItemIdManagers(tradingUser.getTradeByItemIdManagers(), existingItemMainFields);

        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();
        itemForTradeDTOS.addAll(itemsFromFiltersManagerForTradeDTOS);
        itemForTradeDTOS.addAll(itemFromItemForTradeDTOIdManagers);

        return null;
    }


    private Set<ItemForTradeDTO> getTradingItemsFromTradeByFiltersManagers(List<TradeByFiltersManager> tradeByFiltersManagers, Collection<Item> existingItemMainFields) {
        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByFiltersManager tradeByFiltersManager : tradeByFiltersManagers) {
            itemForTradeDTOS.addAll(
                    ItemFilter.filterItems(existingItemMainFields, tradeByFiltersManager.getAppliedFilters())
                            .stream()
                            .map(item -> new ItemForTradeDTO(item, tradeByFiltersManager.getTradeOperationType(), tradeByFiltersManager.getPriority()))
                            .toList());
        }
        return itemForTradeDTOS;
    }

    private Set<ItemForTradeDTO> getTradingItemsFromTradeByItemIdManagers(List<TradeByItemIdManager> tradeByItemIdManagers,
                                                                          Collection<Item> existingItemMainFields) {
        Set<ItemForFastEquals> existingItemsSet = existingItemMainFields.stream().map(item -> (ItemForFastEquals) item).collect(Collectors.toSet());

        Set<ItemForTradeDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByItemIdManager tradeByItemIdManager : tradeByItemIdManagers) {
            Item item = getItemFromSetByItemId(existingItemsSet, tradeByItemIdManager.getItemId());
            if (item != null) {
                itemForTradeDTOS.add(new ItemForTradeDTO(item, tradeByItemIdManager.getTradeOperationType(), tradeByItemIdManager.getPriority()));
            }
        }
        return itemForTradeDTOS;
    }

    private Item getItemFromSetByItemId(Set<ItemForFastEquals> existingItems, String itemId) {
        return existingItems.stream().filter(item -> item.getItemId().equals(itemId)).findFirst().orElse(null);
    }
}
