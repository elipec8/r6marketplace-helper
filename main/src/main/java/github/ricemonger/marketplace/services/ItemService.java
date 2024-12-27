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

    private final TagService tagService;

    private final ItemDatabaseService itemDatabaseService;

    private final ItemSaleDatabaseService saleDatabaseService;

    private final ItemSaleUbiStatsService itemSaleUbiStatsService;

    private final PotentialTradeStatsService potentialTradeStatsService;

    public void saveAllItemLastSales(Collection<? extends ItemMainFieldsI> itemMainFields) {
        saleDatabaseService.saveAll(itemMainFields);
    }

    public void saveAllItemSaleUbiStats(List<GroupedItemDaySalesUbiStats> ubiStats) {
        itemSaleUbiStatsService.saveAll(ubiStats);
    }

    public void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> itemsMainFields) {
        itemDatabaseService.saveAll(getAllItemsFromDbInConjunctionWithUpdatedFields(itemsMainFields));
    }

    public Item getItemById(String itemId) {
        return itemDatabaseService.findById(itemId);
    }

    public List<Item> getAllItemsByFilters(Collection<ItemFilter> filters) {
        List<Item> item = itemDatabaseService.findAll();

        return ItemFilter.filterItems(item, filters);
    }

    public List<Item> getAllItems() {
        return itemDatabaseService.findAll();
    }

    private Set<Item> getAllItemsFromDbInConjunctionWithUpdatedFields(Collection<? extends ItemMainFieldsI> itemMainFields) {
        Set<Tag> tags = new HashSet<>(tagService.getTagsByNames(Set.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")));
        String uncommonTag = tags.stream().filter(tag -> tag.getName().equals("UNCOMMON")).findFirst().get().getValue();
        String rareTag = tags.stream().filter(tag -> tag.getName().equals("RARE")).findFirst().get().getValue();
        String epicTag = tags.stream().filter(tag -> tag.getName().equals("EPIC")).findFirst().get().getValue();
        String legendaryTag = tags.stream().filter(tag -> tag.getName().equals("LEGENDARY")).findFirst().get().getValue();

        Set<Item> existingItems = itemDatabaseService.findAll().stream().map(Item::new).collect(Collectors.toSet());

        Set<Item> updatedItems = itemMainFields.stream().map(Item::new).collect(Collectors.toSet());

        for (Item updatedItem : updatedItems) {
            updatedItem.setRarityByTags(uncommonTag, rareTag, epicTag, legendaryTag);
            Item existingItem = existingItems.stream().filter(existing -> existing.equals(updatedItem)).findFirst().orElse(null);
            if (existingItem == null) {
                log.error("Item with id {} not found, getAllItemsFromDbInConjunctionWithUpdatedFields for this item skipped", updatedItem.getItemId());
                continue;
            }
            updatedItem.setHistoryFields(existingItem);

            boolean itemHistoryFieldsWasCalculatedAtLeastOnce = existingItem.getMonthAveragePrice() != null;

            if (itemHistoryFieldsWasCalculatedAtLeastOnce) {
                updatedItem.updateCurrentPricesPriorities(
                        potentialTradeStatsService.calculatePotentialSellTradeStatsByMaxBuyPrice(updatedItem).getTradePriority(),
                        potentialTradeStatsService.calculatePotentialSellTradeStatsByNextFancySellPrice(updatedItem).getTradePriority(),
                        potentialTradeStatsService.calculatePotentialBuyTradeStatsByMinSellPrice(updatedItem).getTradePriority());
            }
        }
        return updatedItems;
    }
}
