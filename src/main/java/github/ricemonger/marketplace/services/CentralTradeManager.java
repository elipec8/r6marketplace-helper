package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.*;
import github.ricemonger.utils.DTOs.items.Item;
import github.ricemonger.utils.DTOs.items.ItemFilter;
import github.ricemonger.utils.DTOs.items.ItemForCentralTradeManagerDTO;
import github.ricemonger.utils.DTOs.items.ItemForFastEquals;
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
        List<CentralTradeManagerCommand> commands = new LinkedList<>();

        Set<ItemForCentralTradeManagerDTO> itemsForTrading = new HashSet<>();
        Set<ItemForCentralTradeManagerDTO> itemsFromFiltersManagerForTradeDTOS = getTradingItemsFromTradeByFiltersManagers(tradingUser.getTradeByFiltersManagers(), existingItemMainFields);
        Set<ItemForCentralTradeManagerDTO> itemFromItemForTradeDTOIdManagers = getTradingItemsFromTradeByItemIdManagers(tradingUser.getTradeByItemIdManagers(), existingItemMainFields);
        itemsForTrading.addAll(itemsFromFiltersManagerForTradeDTOS);
        itemsForTrading.addAll(itemFromItemForTradeDTOIdManagers);

        return commands;
    }


    private Set<ItemForCentralTradeManagerDTO> getTradingItemsFromTradeByFiltersManagers(List<TradeByFiltersManager> tradeByFiltersManagers, Collection<Item> existingItemMainFields) {
        Set<ItemForCentralTradeManagerDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByFiltersManager tradeByFiltersManager : tradeByFiltersManagers) {
            itemForTradeDTOS.addAll(
                    ItemFilter.filterItems(existingItemMainFields, tradeByFiltersManager.getAppliedFilters())
                            .stream()
                            .map(item -> new ItemForCentralTradeManagerDTO(item, tradeByFiltersManager))
                            .toList());
        }
        return itemForTradeDTOS;
    }

    private Set<ItemForCentralTradeManagerDTO> getTradingItemsFromTradeByItemIdManagers(List<TradeByItemIdManager> tradeByItemIdManagers,
                                                                                        Collection<Item> existingItemMainFields) {
        Set<ItemForFastEquals> existingItemsSet = existingItemMainFields.stream().map(item -> (ItemForFastEquals) item).collect(Collectors.toSet());

        Set<ItemForCentralTradeManagerDTO> itemForTradeDTOS = new HashSet<>();

        for (TradeByItemIdManager tradeByItemIdManager : tradeByItemIdManagers) {
            Item item = getItemFromSetByItemId(existingItemsSet, tradeByItemIdManager.getItemId());
            if (item != null) {
                itemForTradeDTOS.add(new ItemForCentralTradeManagerDTO(item, tradeByItemIdManager));
            }
        }
        return itemForTradeDTOS;
    }

    private Item getItemFromSetByItemId(Set<ItemForFastEquals> existingItems, String itemId) {
        return existingItems.stream().filter(item -> item.getItemId().equals(itemId)).findFirst().orElse(null);
    }
}
