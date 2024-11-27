package github.ricemonger.marketplace.services;

import github.ricemonger.marketplace.services.abstractions.ItemDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleDatabaseService;
import github.ricemonger.marketplace.services.abstractions.ItemSaleUbiStatsService;
import github.ricemonger.utils.DTOs.items.*;
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

    public void saveAllItemLastSales(Collection<? extends ItemMainFieldsI> itemMainFields) {
        saleDatabaseService.saveAll(itemMainFields);
    }

    public void saveAllItemSaleUbiStats(List<GroupedItemDaySalesUbiStats> ubiStats) {
        itemSaleUbiStatsService.saveAll(ubiStats);
    }

    public void saveAllItemsMainFields(Collection<? extends ItemMainFieldsI> itemsMainFields) {
        itemDatabaseService.saveAll(getAllItemsFromDbInConjunctionWithUpdatedMainFields(itemsMainFields));
    }

    public void recalculateAndSaveAllItemsHistoryFields() {
        Set<ItemForFastEquals> items = itemDatabaseService.findAll().stream().map(ItemForFastEquals::new).collect(Collectors.toSet());

        Collection<ItemSale> lastMonthSales = saleDatabaseService.findAllForLastMonth();
        Collection<ItemDaySalesUbiStats> lastMonthSalesUbiStats = itemSaleUbiStatsService.findAllForLastMonth();

        for (Item item : items) {
            Collection<ItemSale> saleStats = getItemSalesForItem(lastMonthSales, item.getItemId());
            Collection<ItemDaySalesUbiStats> ubiSaleStats = getItemDaySalesUbiStatsForItem(lastMonthSalesUbiStats, item.getItemId());
            List<ItemDaySalesStatsByItemId> resultingPerDayStats = getResultingSaleStatsByPeriodForItem(saleStats, ubiSaleStats, item,
                    LocalDate.now().minusDays(30), LocalDate.now());

            TodayPriceStats todayStats = recalculateTodayItemPriceStats(resultingPerDayStats.get(30));
            item.setDaySales(todayStats.quantity());
            item.setDayAveragePrice(todayStats.averagePrice());
            item.setDayMaxPrice(todayStats.maxPrice());
            item.setDayMinPrice(todayStats.minPrice());
            item.setDayMedianPrice(todayStats.medianPrice());

            LastMonthPriceStats lastMonthStats = recalculateLastMonthItemPriceStats(resultingPerDayStats);
            item.setMonthSalesPerDay(lastMonthStats.salesPerDay());
            item.setMonthAveragePrice(lastMonthStats.averagePrice());
            item.setMonthMaxPrice(lastMonthStats.maxPrice());
            item.setMonthMinPrice(lastMonthStats.minPrice());
            item.setMonthMedianPrice(lastMonthStats.medianPrice());

            TreeMap<Integer, Integer> sortedMonthPricesAndQuantities = new TreeMap<>();
            for (ItemDaySalesStatsByItemId dayStat : resultingPerDayStats) {
                for (Map.Entry<Integer, Integer> priceAndQuantity : dayStat.getPriceAndQuantity().entrySet()) {
                    sortedMonthPricesAndQuantities.put(priceAndQuantity.getKey(), sortedMonthPricesAndQuantities.getOrDefault(priceAndQuantity.getKey(), 0) + priceAndQuantity.getValue());
                }
            }

            PricesToBuy pricesToBuy = recalculatePricesToBuy(sortedMonthPricesAndQuantities);
            item.setPriceToBuyIn1Hour(pricesToBuy.priceToBuyIn1Hour());
            item.setPriceToBuyIn6Hours(pricesToBuy.priceToBuyIn6Hours());
            item.setPriceToBuyIn24Hours(pricesToBuy.priceToBuyIn24Hours());
            item.setPriceToBuyIn168Hours(pricesToBuy.priceToBuyIn168Hours());

            PricesToSell pricesToSell = recalculatePricesToSell(sortedMonthPricesAndQuantities);
            item.setPriceToSellIn1Hour(pricesToSell.priceToSellIn1Hour());
            item.setPriceToSellIn6Hours(pricesToSell.priceToSellIn6Hours());
            item.setPriceToSellIn24Hours(pricesToSell.priceToSellIn24Hours());
            item.setPriceToSellIn168Hours(pricesToSell.priceToSellIn168Hours());
        }
        itemDatabaseService.saveAll(items);
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

    private TodayPriceStats recalculateTodayItemPriceStats(ItemDaySalesStatsByItemId todayStats) {
        int todaySalesQuantity = todayStats.getQuantity();
        int todaySumSales = todayStats.getPriceAndQuantity().entrySet().stream().mapToInt(entry -> entry.getKey() * entry.getValue()).sum();
        int todayMedianPrice = 0;

        TreeMap<Integer, Integer> sortedTodayPrices = new TreeMap<>(todayStats.getPriceAndQuantity());

        int currentQuantity = 0;
        for (Map.Entry<Integer, Integer> entry : sortedTodayPrices.entrySet()) {
            currentQuantity += entry.getValue();
            if (currentQuantity >= (float)todaySalesQuantity / 2) {
                todayMedianPrice = entry.getKey();
                break;
            }
        }


        return new TodayPriceStats(todaySalesQuantity,
                todaySalesQuantity == 0 ? 0 : todaySumSales / todaySalesQuantity,
                todayStats.getPriceAndQuantity().keySet().stream().max(Integer::compareTo).orElse(0),
                todayStats.getPriceAndQuantity().keySet().stream().min(Integer::compareTo).orElse(0),
                todayMedianPrice);
    }

    private LastMonthPriceStats recalculateLastMonthItemPriceStats(Collection<ItemDaySalesStatsByItemId> monthStats) {
        int monthSalesQuantity = monthStats.stream().mapToInt(ItemDaySalesStatsByItemId::getQuantity).sum();
        int monthSumSales = 0;
        int monthMaxPrice = 0;
        int monthMinPrice = Integer.MAX_VALUE;
        int monthMedianPrice = 0;
        int currentQuantity = 0;
        TreeMap<Integer, Integer> monthDayMedianPricesAndQuantities = new TreeMap<>();

        for (ItemDaySalesStatsByItemId dayStats : monthStats) {
            for (Map.Entry<Integer, Integer> priceAndQuantity : dayStats.getPriceAndQuantity().entrySet()) {
                if (priceAndQuantity.getKey() > monthMaxPrice) {
                    monthMaxPrice = priceAndQuantity.getKey();
                }
                if (priceAndQuantity.getKey() < monthMinPrice) {
                    monthMinPrice = priceAndQuantity.getKey();
                }
                monthSumSales += priceAndQuantity.getValue() * priceAndQuantity.getKey();
            }

            TreeMap<Integer, Integer> sortedPrices = new TreeMap<>(dayStats.getPriceAndQuantity());
            int daySalesQuantity = dayStats.getQuantity();
            currentQuantity = 0;
            for (Map.Entry<Integer, Integer> entry : sortedPrices.entrySet()) {
                currentQuantity += entry.getValue();
                if (currentQuantity >= (float) daySalesQuantity / 2) {
                    monthDayMedianPricesAndQuantities.put(entry.getKey(),
                            monthDayMedianPricesAndQuantities.getOrDefault(entry.getKey(), 0) + daySalesQuantity);
                    break;
                }
            }
        }
        currentQuantity = 0;
        for (Map.Entry<Integer, Integer> entry : monthDayMedianPricesAndQuantities.entrySet()) {
            currentQuantity += entry.getValue();
            if (currentQuantity >= monthSalesQuantity / 2) {
                monthMedianPrice = entry.getKey();
                break;
            }
        }
        if (monthMinPrice == Integer.MAX_VALUE) {
            monthMinPrice = 0;
        }


        return new LastMonthPriceStats(monthSalesQuantity / 31, monthSalesQuantity == 0 ? 0 : monthSumSales / monthSalesQuantity, monthMaxPrice,
                monthMinPrice,
                monthMedianPrice);
    }

    private PricesToBuy recalculatePricesToBuy(NavigableMap<Integer, Integer> sortedMonthPricesAndQuantities) {
        Integer priceToBuyIn1Hour = 0;
        boolean priceToBuyIn1HourFound = false;
        Integer priceToBuyIn6Hours = 0;
        boolean priceToBuyIn6HoursFound = false;
        Integer priceToBuyIn24Hours = 0;
        boolean priceToBuyIn24HoursFound = false;
        Integer priceToBuyIn168Hours = 0;
        boolean priceToBuyIn168HoursFound = false;

        int currentQuantity = 0;
        for (Map.Entry<Integer, Integer> entry : sortedMonthPricesAndQuantities.entrySet()) {
            currentQuantity += entry.getValue();
            if (!priceToBuyIn1HourFound && currentQuantity >= 720) {
                priceToBuyIn1Hour = entry.getKey();
                priceToBuyIn1HourFound = true;
                break;
            }
            if (!priceToBuyIn6HoursFound && currentQuantity >= 120) {
                priceToBuyIn6Hours = entry.getKey();
                priceToBuyIn6HoursFound = true;
            }
            if (!priceToBuyIn24HoursFound && currentQuantity >= 30) {
                priceToBuyIn24Hours = entry.getKey();
                priceToBuyIn24HoursFound = true;
            }
            if (!priceToBuyIn168HoursFound && currentQuantity >= 4) {
                priceToBuyIn168Hours = entry.getKey();
                priceToBuyIn168HoursFound = true;
            }
        }

        return new PricesToBuy(priceToBuyIn1Hour, priceToBuyIn6Hours, priceToBuyIn24Hours, priceToBuyIn168Hours);
    }

    private PricesToSell recalculatePricesToSell(NavigableMap<Integer, Integer> sortedMonthPricesAndQuantities) {
        Integer priceToSellIn1Hour = 0;
        boolean priceToSellIn1HourFound = false;
        Integer priceToSellIn6Hours = 0;
        boolean priceToSellIn6HoursFound = false;
        Integer priceToSellIn24Hours = 0;
        boolean priceToSellIn24HoursFound = false;
        Integer priceToSellIn168Hours = 0;
        boolean priceToSellIn168HoursFound = false;


        int currentQuantity = 0;
        for (Map.Entry<Integer, Integer> entry : sortedMonthPricesAndQuantities.descendingMap().entrySet()) {
            currentQuantity += entry.getValue();
            if (!priceToSellIn1HourFound && currentQuantity >= 720) {
                priceToSellIn1Hour = entry.getKey();
                priceToSellIn1HourFound = true;
                break;
            }
            if (!priceToSellIn6HoursFound && currentQuantity >= 120) {
                priceToSellIn6Hours = entry.getKey();
                priceToSellIn6HoursFound = true;
            }
            if (!priceToSellIn24HoursFound && currentQuantity >= 30) {
                priceToSellIn24Hours = entry.getKey();
                priceToSellIn24HoursFound = true;
            }
            if (!priceToSellIn168HoursFound && currentQuantity >= 4) {
                priceToSellIn168Hours = entry.getKey();
                priceToSellIn168HoursFound = true;
            }
        }

        return new PricesToSell(priceToSellIn1Hour, priceToSellIn6Hours, priceToSellIn24Hours, priceToSellIn168Hours);
    }

    private List<ItemDaySalesStatsByItemId> getResultingSaleStatsByPeriodForItem(Collection<ItemSale> saleStats,
                                                                                 Collection<ItemDaySalesUbiStats> ubiSaleStats, Item item, LocalDate startDate, LocalDate endDate) {
        List<ItemDaySalesStatsByItemId> resultingPerDayStats = new ArrayList<>();

        for (LocalDate day = startDate; day.isBefore(endDate.plusDays(1)); day = day.plusDays(1)) {
            ItemDaySalesStatsByItemId daySaleStats = new ItemDaySalesStatsByItemId(item.getItemId(), day, saleStats);
            ItemDaySalesStatsByItemId ubiDayStats = new ItemDaySalesStatsByItemId(day, item.getItemId(), ubiSaleStats);

            if (daySaleStats.getQuantity() < (int) (ubiDayStats.getQuantity() * 0.8)) {
                resultingPerDayStats.add(ubiDayStats);
            } else {
                resultingPerDayStats.add(daySaleStats);
            }
        }
        return resultingPerDayStats;
    }

    private Collection<ItemSale> getItemSalesForItem(Collection<ItemSale> itemSales, String itemId) {
        return itemSales.stream().filter(itemSale -> itemSale.getItemId().equals(itemId)).toList();
    }

    private Collection<ItemDaySalesUbiStats> getItemDaySalesUbiStatsForItem(Collection<ItemDaySalesUbiStats> itemDaySalesUbiStats, String itemId) {
        return itemDaySalesUbiStats.stream().filter(ubiStats -> ubiStats.getItemId().equals(itemId)).toList();
    }

    private Set<ItemForFastEquals> getAllItemsFromDbInConjunctionWithUpdatedMainFields(Collection<? extends ItemMainFieldsI> itemMainFields) {
        Set<Tag> tags = new HashSet<>(tagService.getTagsByNames(Set.of("UNCOMMON", "RARE", "EPIC", "LEGENDARY")));
        String uncommonTag = tags.stream().filter(tag -> tag.getName().equals("UNCOMMON")).findFirst().get().getValue();
        String rareTag = tags.stream().filter(tag -> tag.getName().equals("RARE")).findFirst().get().getValue();
        String epicTag = tags.stream().filter(tag -> tag.getName().equals("EPIC")).findFirst().get().getValue();
        String legendaryTag = tags.stream().filter(tag -> tag.getName().equals("LEGENDARY")).findFirst().get().getValue();

        Set<ItemForFastEquals> existingItems = itemDatabaseService.findAll().stream().map(ItemForFastEquals::new).collect(Collectors.toSet());

        Set<ItemForFastEquals> updatedItems = itemMainFields.stream().map(ItemForFastEquals::new).collect(Collectors.toSet());

        for (ItemForFastEquals updatedItem : updatedItems) {
            if (existingItems.contains(updatedItem)) {
                ItemForFastEquals existingItem = existingItems.stream().filter(existing -> existing.equals(updatedItem)).findFirst().get();
                updatedItem.setHistoryFields(existingItem);
            }
            updatedItem.setRarityByTags(uncommonTag, rareTag, epicTag, legendaryTag);
        }
        return updatedItems;
    }

    private record LastMonthPriceStats(int salesPerDay, int averagePrice, int maxPrice, int minPrice, int medianPrice) {
    }

    private record PricesToBuy(Integer priceToBuyIn1Hour, Integer priceToBuyIn6Hours, Integer priceToBuyIn24Hours, Integer priceToBuyIn168Hours) {
    }

    private record PricesToSell(Integer priceToSellIn1Hour, Integer priceToSellIn6Hours, Integer priceToSellIn24Hours,
                                Integer priceToSellIn168Hours) {
    }

    private record TodayPriceStats(int quantity, int averagePrice, int maxPrice, int minPrice, int medianPrice) {
    }
}
