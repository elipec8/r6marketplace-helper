package github.ricemonger.trades_manager.services;

import github.ricemonger.trades_manager.services.abstractions.ItemDatabaseService;
import github.ricemonger.utils.DTOs.common.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemDatabaseService itemDatabaseService;

    public List<Item> getAllItems() {
        return itemDatabaseService.findAll();
    }
}
