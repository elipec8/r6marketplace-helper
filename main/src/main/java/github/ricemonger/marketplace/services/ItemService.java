package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.common.*;
import github.ricemonger.utils.DTOs.personal.ItemFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class ItemService {

    private final ItemDatabaseService itemDatabaseService;

    public Item getItemById(String itemId) {
        return itemDatabaseService.findById(itemId);
    }

    public List<Item> getAllItemsByFilters(Collection<ItemFilter> filters) {
        List<Item> item = itemDatabaseService.findAll();

        return ItemFilter.filterItems(item, filters);
    }
}
