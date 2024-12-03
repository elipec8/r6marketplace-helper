package github.ricemonger.marketplace.services;

import github.ricemonger.utils.DTOs.CentralTradeManagerCommand;
import github.ricemonger.utils.DTOs.ConfigTrades;
import github.ricemonger.utils.DTOs.UserForCentralTradeManager;
import github.ricemonger.utils.DTOs.items.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CentralTradeManager {

    private final UserService userService;

    private final ItemService itemService;

    private final CommonValuesService commonValuesService;

    public void manageAllUsersTrades() {
        ConfigTrades configTrades = commonValuesService.getConfigTrades();

        List<Item> items = itemService.getAllItems();

        List<UserForCentralTradeManager> userForCentralTradeManagers = userService.getAllUserForCentralTradeManagerDTOs(items);

        List<CentralTradeManagerCommand> commands = new ArrayList<>();

        for (UserForCentralTradeManager userForCentralTradeManager : userForCentralTradeManagers) {
            commands.addAll(createCentralTradeManagerCommandsForUser(userForCentralTradeManager, configTrades, items));
        }
    }

    private List<CentralTradeManagerCommand> createCentralTradeManagerCommandsForUser(UserForCentralTradeManager userForCentralTradeManager, ConfigTrades configTrades, Collection<Item> existingItems) {
        List<CentralTradeManagerCommand> commands = new LinkedList<>();

        return commands;
    }
}
