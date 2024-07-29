package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleHistoryDatabaseService;
import github.ricemonger.utils.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ItemStatsService {

    private final ItemDatabaseService itemService;

    private final ItemSaleDatabaseService saleService;

    private final ItemSaleHistoryDatabaseService historyService;

    private final TagService tagService;

    private final CommonValuesService commonValuesService;

    private final ProfitAndPriorityCalculator profitAndPriorityCalculator;

    public void saveAllItemsAndSales(Collection<Item> items) {
        setLimitPricesForItems(items);
        itemService.saveAll(items);
        saleService.saveAll(items);
    }

    private void setLimitPricesForItems(Collection<Item> items) {
        List<Tag> tags = new ArrayList<>(tagService.getTagsByNames(List.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")));
        String uncommonTag = tags.stream().filter(tag -> tag.getName().equals("UNCOMMON")).findFirst().get().getValue();
        String rareTag = tags.stream().filter(tag -> tag.getName().equals("RARE")).findFirst().get().getValue();
        String epicTag = tags.stream().filter(tag -> tag.getName().equals("EPIC")).findFirst().get().getValue();
        String legendaryTag = tags.stream().filter(tag -> tag.getName().equals("LEGENDARY")).findFirst().get().getValue();

        int minUncommon = commonValuesService.getMinimumUncommonPrice();
        int maxUncommon = commonValuesService.getMaximumUncommonPrice();
        int minRare = commonValuesService.getMinimumRarePrice();
        int maxRare = commonValuesService.getMaximumRarePrice();
        int minEpic = commonValuesService.getMinimumEpicPrice();
        int maxEpic = commonValuesService.getMaximumEpicPrice();
        int minLegendary = commonValuesService.getMinimumLegendaryPrice();
        int maxLegendary = commonValuesService.getMaximumLegendaryPrice();
        int minMarketplace = commonValuesService.getMinimumMarketplacePrice();
        int maxMarketplace = commonValuesService.getMaximumMarketplacePrice();

        for (Item item : items) {
            ItemRarity rarity = item.getItemRarity(uncommonTag, rareTag, epicTag, legendaryTag);
            if (rarity == ItemRarity.UNCOMMON) {
                item.setLimitMinPrice(minUncommon);
                item.setLimitMaxPrice(maxUncommon);
            } else if (rarity == ItemRarity.RARE) {
                item.setLimitMinPrice(minRare);
                item.setLimitMaxPrice(maxRare);
            } else if (rarity == ItemRarity.EPIC) {
                item.setLimitMinPrice(minEpic);
                item.setLimitMaxPrice(maxEpic);
            } else if (rarity == ItemRarity.LEGENDARY) {
                item.setLimitMinPrice(minLegendary);
                item.setLimitMaxPrice(maxLegendary);
            } else{
                item.setLimitMinPrice(minMarketplace);
                item.setLimitMaxPrice(maxMarketplace);
            }
        }
    }

    public void calculateAndSaveItemsSaleHistoryStats() {
        List<ItemSaleHistory> histories = new ArrayList<>();
        Collection<Item> items = itemService.findAll();
        Collection<ItemSale> sales = saleService.findAll();

        for (Item item : items) {

            ItemSaleHistory history = new ItemSaleHistory();
            history.setItemId(item.getItemId());

            List<ItemSale> itemSales = sales.stream().filter(sale -> sale.getItemId().equals(item.getItemId())).toList();

            if (itemSales.isEmpty()) {
                histories.add(history);
                continue;
            }

            List<ItemSale> monthSales =
                    itemSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000L))).toList();

            int monthAveragePrice = 0;
            int monthMedianPrice = 0;
            int monthMaxPrice = 0;
            int monthMinPrice = 0;
            int monthSalesPerDay = 0;

            if (!monthSales.isEmpty()) {
                monthAveragePrice = (int) monthSales.stream().mapToInt(ItemSale::getPrice).average().orElse(0);
                monthMedianPrice = monthSales.stream().mapToInt(ItemSale::getPrice).sorted().boxed().toList().get(monthSales.size() / 2);
                monthMaxPrice = monthSales.stream().mapToInt(ItemSale::getPrice).max().orElse(0);
                monthMinPrice = monthSales.stream().mapToInt(ItemSale::getPrice).min().orElse(0);
                monthSalesPerDay = monthSales.size() / 30;
            }

            List<ItemSale> daySales =
                    monthSales.stream().filter(sale -> sale.getSoldAt().after(new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L))).toList();

            int dayAveragePrice = 0;
            int dayMaxPrice = 0;
            int dayMinPrice = 0;
            int dayMedianPrice = 0;
            int daySalesCount = 0;

            if (!daySales.isEmpty()) {
                dayAveragePrice = (int) daySales.stream().mapToInt(ItemSale::getPrice).average().orElse(0);
                dayMaxPrice = daySales.stream().mapToInt(ItemSale::getPrice).max().orElse(0);
                dayMinPrice = daySales.stream().mapToInt(ItemSale::getPrice).min().orElse(0);
                dayMedianPrice = daySales.stream().mapToInt(ItemSale::getPrice).sorted().boxed().toList().get(daySales.size() / 2);
                daySalesCount = daySales.size();
            }

            history.setMonthAveragePrice(monthAveragePrice);
            history.setMonthMedianPrice(monthMedianPrice);
            history.setMonthMaxPrice(monthMaxPrice);
            history.setMonthMinPrice(monthMinPrice);
            history.setMonthSalesPerDay(monthSalesPerDay);

            history.setDayAveragePrice(dayAveragePrice);
            history.setDayMedianPrice(dayMedianPrice);
            history.setDayMaxPrice(dayMaxPrice);
            history.setDayMinPrice(dayMinPrice);
            history.setDaySales(daySalesCount);

            histories.add(history);
        }
        historyService.saveAll(histories);
    }

    public Collection<Item> getAllItemsByFilters(Collection<ItemFilter> filters) {
        Collection<Item> items = itemService.findAll();

        return ItemFilter.filterItems(items, filters);
    }

    public Item getItemById(String itemId) {
        return itemService.findById(itemId);
    }
}
